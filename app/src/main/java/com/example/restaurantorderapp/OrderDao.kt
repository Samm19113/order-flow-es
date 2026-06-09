package com.example.restaurantapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface OrderDao {

    @Insert
    suspend fun insert(order: Order)

    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    suspend fun getAllOrders(): List<Order>
    @Query("SELECT * FROM orders WHERE timestamp BETWEEN :start AND :end")
    suspend fun getOrdersBetween(start: Long, end: Long): List<Order>

    @Query("""
    SELECT * FROM orders
    WHERE timestamp BETWEEN :start AND :end
    AND paymentMethod = :method
""")
    suspend fun getOrdersBetweenByMethod(start: Long, end: Long, method: String): List<Order>


    @Delete
    suspend fun delete(order: Order)

    @Update
    suspend fun update(order: Order)

    @Query("SELECT * FROM orders WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Order?

}
