package com.example.airbillscanner.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.airbillscanner.model.database.entity.CourierEntity
import com.example.airbillscanner.model.database.entity.LoginEntity


@Dao
abstract class CourierDao {

    @Query("SELECT * FROM Courier")
    abstract fun loadCouriers(): LiveData<List<CourierEntity>>


    @Query("DELETE FROM Courier")
    abstract fun deleteCouriers()

    @Insert
    abstract fun insertCouriers(entity: CourierEntity)

}