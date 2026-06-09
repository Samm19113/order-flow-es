package com.example.restaurantapp

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AlertDialog


class OrdersActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var container: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        // ⭐ ADD THESE THREE LINES HERE
        val startInput = findViewById<EditText>(R.id.startDateInput)
        val endInput = findViewById<EditText>(R.id.endDateInput)
        val calcButton = findViewById<Button>(R.id.calcRevenueButton)

        val startTimeInput = findViewById<EditText>(R.id.startTimeInput)
        val endTimeInput = findViewById<EditText>(R.id.endTimeInput)


        // ⭐ AND ADD THE CLICK LISTENER HERE
        calcButton.setOnClickListener {
            val startDateStr = startInput.text.toString()
            val endDateStr = endInput.text.toString()
            val startTimeStr = startTimeInput.text.toString()
            val endTimeStr = endTimeInput.text.toString()

            if (startDateStr.isBlank() || endDateStr.isBlank() ||
                startTimeStr.isBlank() || endTimeStr.isBlank()) {

                Toast.makeText(this, "Enter date and time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

            val startMillis = dateFormat.parse("$startDateStr $startTimeStr")?.time ?: 0L
            var endMillis = dateFormat.parse("$endDateStr $endTimeStr")?.time ?: 0L
            endMillis += 59_999   // ⭐ include the full last minute


            calculateRevenue(startMillis, endMillis)
        }


        // Your existing code
        container = findViewById(R.id.ordersContainer)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "orders.db"
        ).fallbackToDestructiveMigration().build()


        loadOrders()
    }



    override fun onResume() {
        super.onResume()
        loadOrders()
    }

    // ⭐⭐⭐ ADDED — FULL REVENUE CALCULATION (TOTAL, CASH, CARD)
    private fun calculateRevenue(start: Long, end: Long) {
        lifecycleScope.launch {

            // ⭐ Total revenue (all payments)
            val allOrders = db.orderDao().getOrdersBetween(start, end)
            val totalRevenue = allOrders.sumOf { it.total }

            // ⭐ Cash only
            val cashOrders = db.orderDao().getOrdersBetweenByMethod(start, end, "Efectivo")
            val cashRevenue = cashOrders.sumOf { it.total }

            // ⭐ Card only
            val cardOrders = db.orderDao().getOrdersBetweenByMethod(start, end, "Tarjeta")
            val cardRevenue = cardOrders.sumOf { it.total }

            runOnUiThread {
                val revenueText = findViewById<TextView>(R.id.revenueText)
                revenueText.text =
                    "Total: €$totalRevenue\n" +
                            "Efectivo: €$cashRevenue\n" +
                            "Tarjeta: €$cardRevenue"
            }
        }
    }
    // ⭐⭐⭐ END OF ADDED SECTION


    private fun loadOrders() {
        lifecycleScope.launch {
            val orders = db.orderDao().getAllOrders()

            runOnUiThread {

                container.removeAllViews()

                orders.forEach { order ->

                    val layout = LinearLayout(this@OrdersActivity).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(16, 16, 16, 16)
                    }

                    val items = JsonHelper.fromJson(order.itemsJson)

                    val orderText = TextView(this@OrdersActivity).apply {
                        text = """
📍 ${order.address}
📞 ${order.phone}
💳 ${order.paymentMethod}
${if (order.notes.isNotEmpty()) "📝 ${order.notes}" else ""}
💶 €${order.total}
🕒 ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date(order.timestamp))}

🧾 Items:
${
                            items.joinToString("\n\n") { item ->
                                val lines = item.description.split("\n")

                                buildString {
                                    append("• ${lines.first()} : €${item.price}")
                                    for (i in 1 until lines.size) {
                                        append("\n   ${lines[i]}")
                                    }
                                }
                            }
                        }
""".trimIndent()
                    }


                    val buttons = LinearLayout(this@OrdersActivity).apply {
                        orientation = LinearLayout.HORIZONTAL
                    }

                    // ❌ DELETE
                    val deleteBtn = Button(this@OrdersActivity).apply {
                        text = "Eliminar"
                        setOnClickListener {
                            lifecycleScope.launch {
                                db.orderDao().delete(order)
                                loadOrders()
                            }
                        }
                    }

                    // 🔁 REPRINT
                    val printBtn = Button(this@OrdersActivity).apply {
                        text = "Reimprimir"
                        setOnClickListener {
                            val receipt = buildEscPosReceipt(order)

                            Thread {
                                try {
                                    StarPrinterHelper.print(this@OrdersActivity, receipt)
                                } catch (e: Exception) {
                                    runOnUiThread {
                                        Toast.makeText(this@OrdersActivity, e.message, Toast.LENGTH_LONG).show()
                                    }
                                }
                            }.start()
                        }
                    }

                    val editBtn = Button(this@OrdersActivity).apply {
                        text = "Editar"

                        setOnClickListener {
                            val intent = Intent(this@OrdersActivity, EditOrderActivity::class.java)
                            intent.putExtra("orderId", order.id)
                            startActivity(intent)
                        }
                    }





                    buttons.addView(deleteBtn)
                    buttons.addView(printBtn)
                    buttons.addView(editBtn)

                    layout.addView(orderText)
                    layout.addView(buttons)

                    container.addView(layout)
                }

            }
        }
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
}