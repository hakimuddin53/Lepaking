package com.example.lepaking.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lepaking.common.utility.NumberUtility

/* Created by hakim on 2/14/2021. */

@Entity(tableName = "Orders")
class OrderEntity: BaseEntity()
{
    @PrimaryKey
    var orderId: String = NumberUtility.getUUID()
    var code: String = ""
    var timeReceived: String = ""
    var name: String = ""
    var telephoneNumber: String = ""
    var orderType: String = ""
    var pickupTime: String = ""
    var totalPrice: String = ""
    var isPrepared: Boolean = false
}