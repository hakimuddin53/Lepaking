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
    var code: String = ""
    var timeReceived: String = ""
    var name: String = ""
    var telephoneNumber: String = ""
    var orderType: String = ""
    var pickupTime: String = ""
    var totalPrice: String = ""
    var dishName: String = ""
    var dishQuantity: Int = 0
    var dishPrice: String = ""
    var dishRemark: String = ""
}