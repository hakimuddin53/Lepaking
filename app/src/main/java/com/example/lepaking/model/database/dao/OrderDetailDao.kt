package com.example.lepaking.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lepaking.model.database.entity.OrderDetailEntity
import com.example.lepaking.model.ui.OrderDetailModel


@Dao
abstract class OrderDetailDao {

    @Query("SELECT * FROM OrderDetail where code = :code order by timeReceived ")
    abstract fun loadDishDetail(code : String?): LiveData<List<OrderDetailEntity>>

    @Query("SELECT * FROM OrderDetail where code = :code LIMIT 1 ")
    abstract fun loadOrderDetail(code : String?): OrderDetailEntity


    @Query("SELECT distinct code,timeReceived FROM OrderDetail order by timeReceived ")
    abstract fun loadDistinctOrderDetail(): LiveData<List<OrderDetailModel>>


    @Query("DELETE FROM OrderDetail")
    abstract fun deleteAllOrderDetails()

    @Insert
    abstract fun insertOrderDetail(entity: OrderDetailEntity)
}