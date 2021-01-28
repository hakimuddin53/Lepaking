package com.example.airbillscanner.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.airbillscanner.model.database.dao.LoginDao
import com.example.airbillscanner.model.database.entity.LoginEntity
/**
 * Created by cytan on 2/12/2018.
 */

@Database(version = ApplicationDatabase.VERSION,
    entities = [LoginEntity::class],
    exportSchema = false)


abstract class ApplicationDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun loginDao(): LoginDao
}