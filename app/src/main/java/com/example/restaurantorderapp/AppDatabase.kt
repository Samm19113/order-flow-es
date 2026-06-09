package com.example.restaurantapp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Order::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}
