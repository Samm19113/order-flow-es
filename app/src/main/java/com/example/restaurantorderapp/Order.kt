package com.example.restaurantapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val address: String,
    val phone: String,
    val paymentMethod: String,
    val total: Double,
    val timestamp: Long,
    val notes: String,
    val itemsJson: String
)
