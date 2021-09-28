package com.example.lepaking.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lepaking.model.database.entity.OrderDetailEntity
import com.example.lepaking.model.database.entity.OrderEntity
import com.example.lepaking.model.ui.OrderDetailModel


@Dao
abstract class OrderDetailDao {

    @Query("SELECT * FROM OrderDetail where orderId = :orderId order by dishName ")
    abstract fun loadOrderDetail(orderId : String?): LiveData<List<OrderDetailEntity>>

    @Query("SELECT * FROM Orders where orderId = :orderId")
    abstract fun loadOrder(orderId : String?): OrderEntity

    @Query("SELECT * FROM Orders order by timeReceived ")
    abstract fun loadOrders(): LiveData<List<OrderEntity>>

    @Query("DELETE FROM OrderDetail")
    abstract fun deleteAllOrderDetails()

    @Query("DELETE FROM Orders")
    abstract fun deleteAllOrders()

    @Query("SELECT COUNT(*) FROM Orders WHERE isPrepared = 0")
    abstract fun getPendingOrderCount(): Int


    @Query("UPDATE Orders SET isPrepared = 1 WHERE orderId = :orderId")
    abstract fun updatePrepareNowFlag(orderId: String?)

    @Insert
    abstract fun insertOrderDetail(entity: OrderDetailEntity)

    @Insert
    abstract fun insertOrder(entity: OrderEntity)
}