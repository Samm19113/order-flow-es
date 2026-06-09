package com.example.restaurantapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonHelper {

    private val gson = Gson()

    fun toJson(list: List<OrderItem>): String {
        return gson.toJson(list)
    }

    fun fromJson(json: String): MutableList<OrderItem> {
        val type = object : TypeToken<MutableList<OrderItem>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }
}