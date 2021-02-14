package com.example.airbillscanner.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/* Created by hakim on 2/14/2021. */

@Entity(tableName = "Courier")
class CourierEntity: BaseEntity()
{
    @PrimaryKey
    var Id: Int = 0
    var code: String = ""
    var description: String = ""
}