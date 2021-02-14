package com.example.airbillscanner.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.airbillscanner.common.utility.NumberUtility

/* Created by hakim on 2/14/2021. */

@Entity(tableName = "AirbillScanned")
class AirbillScannedEntity: BaseEntity()
{

    @PrimaryKey
    var airbillScannedId: String = NumberUtility.getUUID()
    var barcode: String = ""
    var courierId: Int  = 0
    var orderNumber: String = ""
}