package com.example.lepaking.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/* Created by jhcheng on 2/14/2018. */

@Entity(tableName = "Login")
class LoginEntity: BaseEntity()
{
    @PrimaryKey
    var userId: Int = 0
    var userName: String = ""
    var password: String = ""
}