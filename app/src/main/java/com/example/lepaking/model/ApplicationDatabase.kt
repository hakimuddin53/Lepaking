package com.example.lepaking.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.dao.LoginDao
import com.example.lepaking.model.database.entity.LoginEntity
import com.example.lepaking.model.database.entity.OrderDetailEntity

/**
 * Created by cytan on 2/12/2018.
 */

@Database(version = ApplicationDatabase.VERSION,
    entities = [LoginEntity::class,OrderDetailEntity::class],
    exportSchema = false)


abstract class ApplicationDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun loginDao(): LoginDao
    abstract fun orderDetailDao(): OrderDetailDao
}