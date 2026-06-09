package com.example.restaurantapp

import com.example.restaurantapp.MainActivity.MenuItem

object OrderItemBuilder {

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

val menu = listOf(
        MenuItem("Kebab", 5.0, true, true, true, true, true, true),
        MenuItem("Menu Kebab", 7.5, true, true, true, true, true, true),
        MenuItem("Kebab con falafel", 5.0, false, true, false, false, true, true),
        MenuItem("Menu Kebab con falafel", 7.5, false, true, false, false, true, true),
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

    fun buildItem(
        name: String,
        meat: String,
        extraCheese: Boolean,
        extraMeat: Boolean,
        //mixed: Boolean,
        soloCarne: Boolean,
        sinLechuga: Boolean,
        sinCebolla: Boolean,
        sinTomate: Boolean,
        sinSalsas: Boolean,
        sinSalsaBlanca: Boolean,
        sinSalsaRoja: Boolean,
        conPicante: Boolean
    ): OrderItem {

        val item = menu.find { it.name.equals(name, true) }
            ?: return OrderItem("ERROR", 0.0)

        var price = item.basePrice
        val desc = StringBuilder(item.name)

        if (item.hasMeatChoice) {

            if (meat == "Mixto") {
                price += 0.5
                desc.append("\n+ Mixto (+0.5€)")
            } else {
                desc.append("\n+ $meat")
            }
        }


        if (item.allowsExtras) {
            if (extraCheese) {
                price += 1.0
                desc.append("\n+ queso")
            }
            if (extraMeat) {
                price += 1.0
                desc.append("\n+ extra carne")
            }
        }

        //if (item.allowsMixed && mixed) {
          //  price += 0.5
            //desc.append(" + Mixto")
        //}

        if (soloCarne) {
            price += 1.0
            desc.append("\n+ Solo Carne")
        }

        if (sinLechuga) {
            price += 1.0
            desc.append("\n- sin lechuga (+1€)")
        }
        if (sinCebolla) desc.append("\n- sin cebolla")
        if (sinTomate) desc.append("\n- sin tomate")

        if (sinSalsas) desc.append("\n- sin salsas")
        if (sinSalsaBlanca) desc.append("\n- sin salsa blanca")
        if (sinSalsaRoja) desc.append("\n- sin salsa roja")

        if (conPicante) desc.append("\n+ picante")

        return OrderItem(desc.toString(), price)
    }
}
