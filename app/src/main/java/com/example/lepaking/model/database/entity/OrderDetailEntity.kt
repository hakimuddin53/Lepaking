package com.example.lepaking.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lepaking.common.utility.NumberUtility

/* Created by hakim on 2/14/2021. */

@Entity(tableName = "OrderDetail")
class OrderDetailEntity: BaseEntity()
{
    @PrimaryKey
    var orderDetailId: String = NumberUtility.getUUID()
    var orderId: String = ""
    var dishName: String = ""
    var dishQuantity: Int = 0
    var dishPrice: String = ""
    var dishRemark: String = ""
}