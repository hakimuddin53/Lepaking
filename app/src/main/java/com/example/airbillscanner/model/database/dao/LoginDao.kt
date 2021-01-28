package com.example.airbillscanner.model.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.airbillscanner.model.database.entity.LoginEntity


@Dao
abstract class LoginDao {

    @Query("SELECT * FROM Login WHERE userName = :username")
    abstract fun loadUser(username: String): LoginEntity
}