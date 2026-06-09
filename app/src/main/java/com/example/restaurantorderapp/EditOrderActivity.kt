package com.example.restaurantapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import androidx.appcompat.app.AlertDialog
class EditOrderActivity : AppCompatActivity() {

    private lateinit var addItemBtn: Button
    private lateinit var db: AppDatabase
    private lateinit var orderList: LinearLayout
    private lateinit var totalText: TextView
    private lateinit var saveBtn: Button

    private var orderId: Int = -1
    private val items = mutableListOf<OrderItem>()
    private var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_order)

        addItemBtn = findViewById(R.id.addItemButton)
        addItemBtn.setOnClickListener { showAddItemDialog() }

        orderList = findViewById(R.id.editOrderList)
        totalText = findViewById(R.id.editTotalText)
        saveBtn = findViewById(R.id.saveEditButton)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "orders.db"
        ).fallbackToDestructiveMigration().build()

        orderId = intent.getIntExtra("orderId", -1)

        loadOrder()
    }

    private fun showAddItemDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)

        val auto = dialogView.findViewById<AutoCompleteTextView>(R.id.dialogAutoComplete)
        val meatSpinner = dialogView.findViewById<Spinner>(R.id.dialogMeatSpinner)
        val extraCheese = dialogView.findViewById<CheckBox>(R.id.dialogExtraCheese)
        val extraMeat = dialogView.findViewById<CheckBox>(R.id.dialogExtraMeat)
        //val mixed = dialogView.findViewById<CheckBox>(R.id.dialogMixedMeat)
        val soloCarne = dialogView.findViewById<CheckBox>(R.id.dialogSoloCarne)
        val sinLechuga = dialogView.findViewById<CheckBox>(R.id.dialogSinLechuga)
        val sinCebolla = dialogView.findViewById<CheckBox>(R.id.dialogSinCebolla)
        val sinTomate = dialogView.findViewById<CheckBox>(R.id.dialogSinTomate)
        val sinSalsas = dialogView.findViewById<CheckBox>(R.id.dialogsinSalsas)
        val sinSalsaBlanca = dialogView.findViewById<CheckBox>(R.id.dialogSinSalsaBlanca)
        val sinSalsaRoja = dialogView.findViewById<CheckBox>(R.id.dialogSinSalsaRoja)
        val conPicante = dialogView.findViewById<CheckBox>(R.id.dialogConPicante)

        auto.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                OrderItemBuilder.menu.map { it.name }
            )
        )

        meatSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listOf("", "Mixto", "Pollo", "Ternera")
        )

        AlertDialog.Builder(this)
            .setTitle("Añadir artículo")
            .setView(dialogView)
            .setPositiveButton("Añadir") { _, _ ->
                val name = auto.text.toString()
                val meat = meatSpinner.selectedItem.toString()

                val item = OrderItemBuilder.buildItem(
                    name = name,
                    meat = meat,
                    extraCheese = extraCheese.isChecked,
                    extraMeat = extraMeat.isChecked,
                    //mixed = mixed.isChecked,
                    soloCarne = soloCarne.isChecked,
                    sinLechuga = sinLechuga.isChecked,
                    sinCebolla = sinCebolla.isChecked,
                    sinTomate = sinTomate.isChecked,
                    sinSalsas = sinSalsas.isChecked,
                    sinSalsaBlanca = sinSalsaBlanca.isChecked,
                    sinSalsaRoja = sinSalsaRoja.isChecked,
                    conPicante = conPicante.isChecked
                )

                items.add(item)
                render()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun loadOrder() {
        lifecycleScope.launch {
            val order = db.orderDao().getById(orderId) ?: return@launch

            items.clear()
            items.addAll(JsonHelper.fromJson(order.itemsJson))

            render()

            saveBtn.setOnClickListener {
                val updated = order.copy(
                    itemsJson = JsonHelper.toJson(items),
                    total = total
                )

                lifecycleScope.launch {
                    db.orderDao().update(updated)
                    finish()
                }
            }
        }
    }


    private fun render() {
        orderList.removeAllViews()
        total = 0.0

        items.forEachIndexed { index, item ->

            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL

            val tv = TextView(this)
            tv.text = "${item.description} : €${item.price}"
            tv.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

            val deleteBtn = Button(this)
            deleteBtn.text = "❌"
            deleteBtn.setOnClickListener {
                items.removeAt(index)
                render()
            }

            row.addView(tv)
            row.addView(deleteBtn)

            orderList.addView(row)

            total += item.price
        }

        totalText.text = "Total: €$total"
    }
}
