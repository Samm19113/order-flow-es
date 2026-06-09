package com.example.restaurantapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.Date
import android.content.pm.PackageManager
import android.widget.Button
import android.util.Log
import android.text.Editable
import android.text.TextWatcher
import com.example.restaurantapp.OrderItemBuilder.MenuItem
import java.net.URL
import java.net.HttpURLConnection
import org.json.JSONObject





class MainActivity : AppCompatActivity() {

    private val currentOrderItems = mutableListOf<OrderItem>()

    data class MenuItem(
        val name: String,
        val basePrice: Double,
        val hasMeatChoice: Boolean,
        val allowsExtras: Boolean,
        val allowsMixed: Boolean,
        val allowsSoloCarne: Boolean,
        val allowsSinVerduras: Boolean,
        val allowsSinSalsas: Boolean
    )

    private val menu = listOf(
        MenuItem("Kebab", 5.0, true, true, true, true, true, true),
        MenuItem("Menu Kebab", 7.5, true, true, true, true, true, true),
        MenuItem("Menu Kebab con falafel", 7.5, false, true, false, false, true, true),
        MenuItem("Kebab con falafel", 5.0, false, true, false, false, true, true),
        MenuItem("Kebab con Patatas dentro", 6.0, true, true, true, true, true, true),
        MenuItem("Menu Taco Kebab", 9.0, true, true, true, true, true, true),
        MenuItem("Taco Kebab", 6.0, true, true, true, true, true, true),
        MenuItem("Menu Taco Crispy", 9.0, false, true, false, false, false, true),
        MenuItem("Taco Crispy", 6.0, false, true, false, false, false, true),
        MenuItem("Menu Pareja Kebab 1", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Pareja Kebab 2", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Grupo Kebab 1", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Grupo Kebab 2", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Grupo Kebab 3", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Kebab 1", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Kebab 2", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Kebab 3", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Kebab 4", 7.0, true, true, true, true, true, true),

        MenuItem("Durum", 6.0, true, true, true, true, true, true),
        MenuItem("Menu Durum", 8.5, true, true, true, true, true, true),
        MenuItem("Menu Durum Loco", 7.0, true, true, true, true, true, true),
        MenuItem("Menu Durum Crispy", 7.0, false, true, false, false, true, true),
        MenuItem("Durum burrito", 6.5, false, true, false, false, true, true),
        MenuItem("Durum con Patatas dentro", 6.5, true, true, true, true, true, true),
        MenuItem("Menu Pareja Durum 1", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Pareja Durum 2", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Grupo Durum 1", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Grupo Durum 2", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Grupo Durum 3", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Durum 1", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Durum 2", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Durum 3", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Amigos Durum 4", 8.0, true, true, true, true, true, true),
        MenuItem("Menu Durum XXL", 13.0, true, true, true, true, true, true),
        MenuItem("Durum XXL", 10.0, true, true, true, true, true, true),

        MenuItem("Lahmacun", 6.5, true, true, true, true, true, true),
        MenuItem("Menu Lahmacun", 9.0, true, true, true, true, true, true),
        MenuItem("Lahmacun con falafel", 6.0, false, true, false, false, true, true),
        MenuItem("Lahmacun con Patatas dentro", 7.0, true, true, true, true, true, true),

        MenuItem("Plato Gratinado Peq.", 5.0, true, true, true, true, true, true),
        MenuItem("Plato Gratinado Med.", 7.0, true, true, true, true, true, true),
        MenuItem("Plato Gratinado Grande", 9.0, true, true, true, true, true, true),
        MenuItem("Plato Comb.", 7.5, true, true, true, true, true, true),
        MenuItem("Menu Plato", 9.0, true, true, true, true, true, true),
        MenuItem("Menu Plato con Arroz", 10.0, true, true, true, true, true, true),
        MenuItem("Plato Comb. falafel", 7.5, false, true, false, false, true, true),
        MenuItem("Plato degustacion", 8.5, false, true, false, false, true, true),
        MenuItem("Plato XXL", 13.0, false, true, false, false, true, true),

        MenuItem("Especial Estambul", 9.5, true, true, true, true, true, true),
        MenuItem("Ensalada", 4.5, true, false, true, true, true, true),
        MenuItem("Ensalada de la Casa", 6.0, true, true, true, true, true, true),
        MenuItem("Ensalada Especial", 7.5, true, true, true, true, true, true),
        MenuItem("Hamburguesa", 5.0, true, true, false, false, true, true),
        MenuItem("Patatas", 3.0, false, true, false, false, false, true),
        MenuItem("Menu Patatas", 0.0, false, true, false, false, false, true),
        MenuItem("Patatas grande", 4.5, false, true, false, false, false, true),
        MenuItem("Patatas bravas", 3.5, false, true, false, false, false, true),
        MenuItem("Patatas deluxe", 4.0, false, true, false, false, false, true),
        MenuItem("Arroz", 3.5, false, true, false, false, false, true),
        MenuItem("falafel", 4.5, false, true, false, false, false, true),

        MenuItem("Pedrata", 4.0, true, true, true, false, true, true),
        MenuItem("Menu Pedrata", 6.0, true, true, true, false, true, true),
        MenuItem("Pedrata mediana", 6.0, true, true, true, false, true, true),
        MenuItem("Menu Pedrata mediana", 8.0, true, true, true, false, true, true),
        MenuItem("Pedrata grande", 8.0, true, true, true, false, true, true),
        MenuItem("Menu Pedrata grande", 10.5, true, true, true, false, true, true),
        MenuItem("Pedrata XXL", 12.0, true, true, true, false, true, true),

        MenuItem("Seekh Kebab", 2.0, false, true, false, false, true, true),
        MenuItem("Samosa", 2.0, false, true, false, false, false, true),
        MenuItem("Alitas de pollo", 4.5, false, true, false, false, false, true),
        MenuItem("Menu Alitas de pollo", 7.0, false, true, false, false, false, true),
        MenuItem("Rabas de calamar", 8.5, false, true, false, false, true, true),
        MenuItem("Nuggets de pollo", 4.5, false, true, false, false, false, true),
        MenuItem("Menu Nuggets de pollo", 7.0, false, true, false, false, false, true),
        MenuItem("Popcorn de pollo", 4.5, false, true, false, false, false, true),
        MenuItem("Pechuga de pollo rebozada", 6.5, false, true, false, false, true, true),
        MenuItem("Menu Pechuga de pollo", 8.0, false, true, false, false, true, true),

        MenuItem("Tar. Blanca", 0.5, false, false, false, false, false, false),
        MenuItem("Tar. Roja", 0.5, false, false, false, false, false, false),
        MenuItem("Tar. Picante", 0.5, false, false, false, false, false, false),

        MenuItem("Pan", 1.0, false, true, false, false, true, true),

        MenuItem("Menu Pollo Broaster Med.", 9.0, false, true, false, false, true, true),
        MenuItem("Menu Pollo Broaster Entero", 17.0, false, true, false, false, true, true),

        MenuItem("Menu Pollo Grande", 15.0, false, true, false, false, true, true),
        MenuItem("Aros de cebolla", 4.5, false, true, false, false, false, true),
        MenuItem("Tiramisu", 3.5, false, false, false, false, false, false),
        MenuItem("Tarta de chocolate", 2.5, false, false, false, false, false, false),
        MenuItem("Cornetto clasico", 2.0, false, false, false, false, false, false),
        MenuItem("Helado Almendrado", 3.0, false, false, false, false, false, false),

        MenuItem("Coke Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Coke Cero Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Coke", 2.0, false, false, false, false, false, false),
        MenuItem("Coke Cero", 2.0, false, false, false, false, false, false),
        MenuItem("Coke Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Coke Cero Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Coke Grande (+€1)", 1.0, false, false, false, false, false, false),
        MenuItem("Coke Cero Grande (+€1)", 1.0, false, false, false, false, false, false),
        //MenuItem("Coke Grande Broaster", 0.0, false, false, false, false, false, false),
        //MenuItem("Coke Grande Cero Broaster", 0.0, false, false, false, false, false, false),
        MenuItem("Coke Grande", 3.5, false, false, false, false, false, false),
        MenuItem("Coke Cero Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Pepsi Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Pepsi Cero Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Pepsi", 2.0, false, false, false, false, false, false),
        MenuItem("Pepsi Cero", 2.0, false, false, false, false, false, false),
        MenuItem("Pepsi Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Pepsi Cero Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Pepsi Cero Grande (+€1)", 1.0, false, false, false, false, false, false),
        MenuItem("Pepsi Grande (+€1)", 1.0, false, false, false, false, false, false),
        MenuItem("Pepsi Grande", 3.5, false, false, false, false, false, false),
        MenuItem("Pepsi Cero Grande", 3.5, false, false, false, false, false, false),
        //MenuItem("Pepsi Grande Broaster", 0.0, false, false, false, false, false, false),
        //MenuItem("Pepsi Grande Cero Broaster", 0.0, false, false, false, false, false, false),

        MenuItem("KAS Naranja Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("KAS Naranja", 2.0, false, false, false, false, false, false),
        MenuItem("KAS Naranja Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("KAS Naranja Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("KAS Naranja Grande", 3.5, false, false, false, false, false, false),

        MenuItem("KAS Limón Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("KAS Limón", 2.0, false, false, false, false, false, false),
        MenuItem("KAS Limón Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("KAS Limón Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("KAS Limón Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Fanta Naranja Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Fanta Naranja", 2.0, false, false, false, false, false, false),
        MenuItem("Fanta Naranja Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Fanta Naranja Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("Fanta Naranja Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Fanta Limón Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Fanta Limón", 2.0, false, false, false, false, false, false),
        MenuItem("Fanta Limón Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Fanta Limón Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("Fanta Limón Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Nestea Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Nestea", 2.0, false, false, false, false, false, false),
        MenuItem("Nestea Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Nestea Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("Nestea Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Sprite Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Sprite", 2.0, false, false, false, false, false, false),
        MenuItem("Sprite Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Sprite Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("Sprite Grande", 3.5, false, false, false, false, false, false),

        MenuItem("7up Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("7up", 2.0, false, false, false, false, false, false),
        MenuItem("7up Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("7up Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("7up Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Bifruta Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Bifruta", 2.0, false, false, false, false, false, false),

        MenuItem("Aquarius Naranja Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Aquarius Naranja", 2.0, false, false, false, false, false, false),
        MenuItem("Aquarius Naranja Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Aquarius Naranja Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("Aquarius Naranja Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Aquarius Limón Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Aquarius Limón", 2.0, false, false, false, false, false, false),
        MenuItem("Aquarius Limón Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Aquarius Limón Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("Aquarius Limón Grande", 3.5, false, false, false, false, false, false),

        MenuItem("Redbull Incl.", 1.0, false, false, false, false, false, false),
        MenuItem("Redbull", 3.0, false, false, false, false, false, false),

        MenuItem("Monster Incl.", 1.0, false, false, false, false, false, false),
        MenuItem("Monster", 3.5, false, false, false, false, false, false),

        MenuItem("Agua Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Agua", 1.5, false, false, false, false, false, false),

        MenuItem("Agua Grande Incl.", 0.0, false, false, false, false, false, false),
        MenuItem("Agua Grande (+1€)", 1.0, false, false, false, false, false, false),
        MenuItem("Agua Grande", 2.5, false, false, false, false, false, false)
        )

    private lateinit var autoComplete: AutoCompleteTextView

    private lateinit var qtySpinner: Spinner
    private lateinit var meatSpinner: Spinner
    //private lateinit var drinkSpinner: Spinner
    private lateinit var extraCheese: CheckBox
    private lateinit var extraMeat: CheckBox
    //private lateinit var mixedMeat: CheckBox
    private lateinit var soloCarne: CheckBox
    private lateinit var sinLechuga: CheckBox
    private lateinit var sinCebolla: CheckBox
    private lateinit var sinTomate: CheckBox
    private lateinit var sinSalsaBlanca: CheckBox
    private lateinit var sinSalsaRoja: CheckBox
    private lateinit var conPicante: CheckBox
    private lateinit var sinSalsas: CheckBox
    private lateinit var itemNoteInput: EditText
    private lateinit var addButton: Button
    private lateinit var totalText: TextView
    private lateinit var orderList: LinearLayout

    private lateinit var notesInput: EditText
    private lateinit var addressInput: AutoCompleteTextView
    private lateinit var phoneInput: EditText
    private lateinit var paymentGroup: RadioGroup
    private lateinit var payCash: RadioButton
    private lateinit var payCard: RadioButton
    private lateinit var finalizeButton: Button

    private var total = 0.0

    private val db: AppDatabase by lazy {
        androidx.room.Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "orders.db"
        )
            .fallbackToDestructiveMigration()   // ⭐ add this
            .build()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.BLUETOOTH_CONNECT,
                    android.Manifest.permission.BLUETOOTH_SCAN
                ),
                1001
            )
        }


        autoComplete = findViewById(R.id.autoCompleteItem)
        qtySpinner = findViewById(R.id.qtySpinner)
        meatSpinner = findViewById(R.id.meatSpinner)
        //drinkSpinner = findViewById(R.id.drinkSpinner)
        extraCheese = findViewById(R.id.extraCheese)
        extraMeat = findViewById(R.id.extraMeat)
        //mixedMeat = findViewById(R.id.mixedMeat)
        soloCarne = findViewById(R.id.soloCarne)
        sinLechuga = findViewById(R.id.sinLechuga)
        sinCebolla = findViewById(R.id.sinCebolla)
        sinTomate = findViewById(R.id.sinTomate)
        sinSalsaBlanca = findViewById(R.id.sinSalsaBlanca)
        sinSalsaRoja = findViewById(R.id.sinSalsaRoja)
        conPicante = findViewById(R.id.conPicante)
        sinSalsas = findViewById(R.id.sinSalsas)
        itemNoteInput = findViewById(R.id.itemNoteInput)
        addButton = findViewById(R.id.addButton)
        totalText = findViewById(R.id.totalText)
        orderList = findViewById(R.id.orderList)

        addressInput = findViewById(R.id.addressInput)

        addressInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()

                if (query.length < 3) return

                val url = "https://photon.komoot.io/api/?q=${query}" +
                        "&bbox=-3.85,43.43,-3.75,43.50" +
                        "&limit=10"

                Thread {
                    try {
                        val connection = URL(url).openConnection() as HttpURLConnection
                        connection.requestMethod = "GET"

                        val response = connection.inputStream.bufferedReader().readText()

                        val json = JSONObject(response)
                        val features = json.getJSONArray("features")

                        val suggestions = mutableListOf<String>()

                        for (i in 0 until features.length()) {
                            val prop = features.getJSONObject(i).getJSONObject("properties")
                            val name = prop.optString("name")
                            val city = prop.optString("city")
                            val street = prop.optString("street")
                            val housenumber = prop.optString("housenumber")

                            val full = listOf(name, street, housenumber, city)
                                .filter { it.isNotEmpty() }
                                .joinToString(", ")

                            if (full.isNotEmpty() && city.equals("Santander", ignoreCase = true)) {
                                suggestions.add(full)
                            }
                        }

                        runOnUiThread {
                            val adapter = ArrayAdapter(
                                this@MainActivity,
                                android.R.layout.simple_dropdown_item_1line,
                                suggestions
                            )
                            addressInput.setAdapter(adapter)
                            addressInput.showDropDown()
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }.start()
            }
        })

        phoneInput = findViewById(R.id.phoneInput)
        notesInput = findViewById(R.id.notesInput)
        paymentGroup = findViewById(R.id.paymentGroup)
        payCash = findViewById(R.id.payCash)
        payCard = findViewById(R.id.payCard)
        finalizeButton = findViewById(R.id.finalizeButton)


        findViewById<Button>(R.id.viewOrdersButton).setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }


        setupAutocomplete()
        setupSpinner()
        setupQtySpinner()
        //setupDrinkSpinner()
        addButton.setOnClickListener {
            addItemToOrder()
        }

        finalizeButton.setOnClickListener {
            finalizeOrder()
        }

    }

    private fun setupQtySpinner() {
        val qtyList = listOf(1, 2, 3, 4, 5, 6)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            qtyList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        qtySpinner.adapter = adapter
        qtySpinner.setSelection(0) // default = 1
    }

    private fun setupAutocomplete() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            menu.map { it.name }
        )
        autoComplete.setAdapter(adapter)
    }

    //private fun setupDrinkSpinner() {
        //val drinks = listOf("No drink", "Coke", "Coke Cero", "Coke Grande", "Coke Cero Grande", "Pepsi", "Pepsi Cero", "Pepsi Grande", "Pepsi Cero Grande", "Fanta Naranja", "Fanta Limón", "Fanta Naranja Grande", "Fanta Limón Grande", "Aquarius Naranja", "Aquarius Limón", "Aquarius Naranja Grande", "Aquarius Limón Grande", "Nestea", "Nestea Grande", "Sprite", "Sprite Grande", "7up", "7up Grande","KAS Naranja", "KAS Limón", "KAS Naranja Grande", "KAS Limón Grande", "Agua", "Agua Grande", "Redbull", "Monster")

        //val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, drinks)
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //drinkSpinner.adapter = adapter
    //}
    private fun setupSpinner() {
        val meats = listOf("", "Mixto", "Pollo", "Ternera")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, meats)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        meatSpinner.adapter = adapter
    }

    private fun renderOrderList() {
        orderList.removeAllViews()
        total = 0.0

        currentOrderItems.forEachIndexed { index, item ->

            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL

            val tv = TextView(this)
            tv.text = "${item.description} : €${item.price}"
            tv.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

            val deleteBtn = Button(this)
            deleteBtn.text = "❌"
            deleteBtn.setOnClickListener {
                currentOrderItems.removeAt(index)
                renderOrderList()
            }

            row.addView(tv)
            row.addView(deleteBtn)

            orderList.addView(row)

            total += item.price
        }

        totalText.text = "Total: €$total"
    }

    private fun addItemToOrder() {
        val name = autoComplete.text.toString()
        val item = menu.find { it.name.equals(name, true) } ?: return

        var price = item.basePrice
        val itemNote = itemNoteInput.text.toString().trim()
        val description = StringBuilder(item.name)

//--------------------------------------------------------------------------
        //val drink = drinkSpinner.selectedItem.toString()

        //if (drink != "No drink") {
            //description.append(" + $drink")

            //val isMenu = item.name.contains("menu", ignoreCase = true)

            //val drinkPrice = when {

                // 🔴 ENERGY DRINKS
                //drink == "Redbull" -> if (isMenu) 1.0 else 3.0
                //drink == "Monster" -> if (isMenu) 1.0 else 3.5

                // 🔵 LARGE WATER
                //drink == "Agua Grande" -> if (isMenu) 1.0 else 2.5

                // 🟣 LARGE SOFT DRINKS
                //drink == "Coke Grande" ||
                        //drink == "Coke Cero Grande" ||
                        //drink == "Pepsi Grande" ||
                        //drink == "Pepsi Cero Grande" ||
                        //drink == "Fanta Naranja Grande" ||
                        //drink == "Fanta Limón Grande" ||
                        //drink == "KAS Naranja Grande" ||
                        //drink == "KAS Limón Grande" ||
                        //drink == "Aquarius Naranja Grande" ||
                        //drink == "Aquarius Limón Grande" ||
                        //drink == "Sprite Grande" ||
                        //drink == "Nestea Grande" ||
                        //drink == "7up Grande" -> if (isMenu) 1.0 else 3.5

                // 🟢 DEFAULT DRINKS
                //else -> {
                    //if (isMenu) 0.0 else 2.0
                //}
            //}

            //price += drinkPrice
//}
//---------------------------------------------------------

        if (item.hasMeatChoice) {
            val meat = meatSpinner.selectedItem.toString()

            if (meat == "Mixto") {
                price += 0.5
                description.append("\n+ Mixto (+0.5€)")
            }
         else if (meat != "Mixto") {
                description.append("\n+ $meat")
            }
        }


        if (item.allowsExtras) {
            if (extraCheese.isChecked) {
                price += 1.0
                description.append("\n+ queso (+1€)")
            }
            if (extraMeat.isChecked) {
                price += 1.0
                description.append("\n+ extra carne (+1€)")
            }
        }

        // SOLO CARNE (+1€)
        if (soloCarne.isChecked) {
            price += 1.0
            description.append("\n+ solo carne (+1€)")
        }

// NO VEGETABLES
        if (sinLechuga.isChecked) {
            price += 1.0
            description.append("\n- sin lechuga (+1€)")
        }
        if (sinCebolla.isChecked) description.append("\n- sin cebolla")
        if (sinTomate.isChecked) description.append(" - sin tomate")

// NO SAUCES
        if (sinSalsas.isChecked) description.append("\n- sin salsas")
        if (sinSalsaBlanca.isChecked) description.append("\n- sin salsa blanca")
        if (sinSalsaRoja.isChecked) description.append("\n- sin salsa roja")

// NO SPICY
        if (conPicante.isChecked) description.append("\n+ picante")


        //if (item.allowsMixed && mixedMeat.isChecked) {
            //price += 0.5
            //description.append(" + Mixto (+0.5€)")
        //}

        val qty = qtySpinner.selectedItem as Int

        val finalPrice = price * qty

        if (itemNote.isNotEmpty()) {
            description.append("\n Nota: $itemNote")
        }

        val finalDescription = if (qty > 1) {
            "$qty x ${description}"
        } else {
            description.toString()
        }

        val orderItem = OrderItem(finalDescription, finalPrice)
        currentOrderItems.add(orderItem)

        renderOrderList()

        resetInputs()
    }

    private fun finalizeOrder() {
        val address = addressInput.text.toString()
        val phone = phoneInput.text.toString()
        val notes = notesInput.text.toString().trim()
        val payment = if (payCash.isChecked) "Efectivo" else "Tarjeta"

        if (total == 0.0) {
            Toast.makeText(this, "No items in order", Toast.LENGTH_SHORT).show()
            return
        }

        val itemsJson = JsonHelper.toJson(currentOrderItems)

        val order = Order(
            address = address,
            phone = phone,
            paymentMethod = payment,
            total = total,
            timestamp = System.currentTimeMillis(),
            notes = notes,
            itemsJson = itemsJson
        )

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.BLUETOOTH_SCAN)
                != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.BLUETOOTH_CONNECT,
                        android.Manifest.permission.BLUETOOTH_SCAN
                    ),
                    1001
                )
                return   // stop finalizeOrder() completely
            }
        }


        lifecycleScope.launch {
            db.orderDao().insert(order)

            // ✅ BUILD RECEIPT
            val receipt = buildEscPosReceipt(order)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                if (checkSelfPermission(android.Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT), 1001)
                    return@launch   // stop finalizeOrder until permission granted
                }
            }


            // ✅ PRINT (on background thread)
            try {
                Thread {
                    try {
                        StarPrinterHelper.print(this@MainActivity, receipt)

                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Printed successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } catch (e: Exception) {
                        runOnUiThread {
                            Toast.makeText(
                                this@MainActivity,
                                "Print failed: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }.start()

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Print failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            Toast.makeText(this@MainActivity, "Order saved", Toast.LENGTH_SHORT).show()
        }

        resetOrderSession()
    }



    private fun resetInputs() {
        // Reset item name
        autoComplete.text.clear()
        qtySpinner.setSelection(0) // back to 1
        // Reset meat spinner
        meatSpinner.setSelection(0)

        // Reset extras
        extraCheese.isChecked = false
        extraMeat.isChecked = false

        // Reset meat options
        //mixedMeat.isChecked = false
        soloCarne.isChecked = false

        // Reset vegetables
        sinLechuga.isChecked = false
        sinCebolla.isChecked = false
        sinTomate.isChecked = false

        // Reset sauces
        sinSalsas.isChecked = false
        sinSalsaBlanca.isChecked = false
        sinSalsaRoja.isChecked = false

        // Reset spicy
        conPicante.isChecked = false
        itemNoteInput.text.clear()
    }


    private fun resetOrderSession() {
        currentOrderItems.clear()   // ⭐ clear data model
        renderOrderList()           // ⭐ re-render empty UI
        addressInput.text.clear()
        total = 0.0
        totalText.text = "Total: €0"
        phoneInput.text.clear()
        notesInput.text.clear()
        payCash.isChecked = true
    }

    private fun buildEscPosReceipt(order: Order): String {
        val date = SimpleDateFormat("dd/MM/yyyy HH:mm").format(Date(order.timestamp))
        val items = JsonHelper.fromJson(order.itemsJson)

        return """
KEBAB FLORANES
C. Floranes, 50, 39010, Santander (Cantabria)
Telf.:692303204

ROYAL DONER KEBAB
C. San Luis, 20, 39010, Santander (Cantabria)
Telf.: 654698630

*** TICKET ***
-----------------------------
${items.joinToString("\n\n") { item ->
    val lines = item.description.split("\n")

    buildString {
        append("• ${lines.first()} : €${item.price}")
        for (i in 1 until lines.size) {
            append("\n   ${lines[i]}")
        }
    }
}}


Tel: ${order.phone}
Dirección: ${order.address}
${if (order.notes.isNotEmpty()) "Notas: ${order.notes}\n" else ""}

-----------------------------
Pago: ${order.paymentMethod}
TOTAL: €${order.total}

$date

¡Gracias por su compra!
***
""".trimIndent()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1001) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Bluetooth permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Bluetooth permission required for printing", Toast.LENGTH_LONG).show()
            }
        }
    }


}