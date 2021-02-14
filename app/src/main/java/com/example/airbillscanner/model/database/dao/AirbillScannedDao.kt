package com.example.airbillscanner.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.airbillscanner.model.database.entity.AirbillScannedEntity
import com.example.airbillscanner.model.database.entity.CourierEntity
import com.example.airbillscanner.model.database.entity.LoginEntity


@Dao
abstract class AirbillScannedDao {

    @Query("SELECT * FROM AirbillScanned")
    abstract fun loadAirBillScanned(): LiveData<List<AirbillScannedEntity>>


    @Query("DELETE FROM AirbillScanned")
    abstract fun deleteAirBillScanned()

    @Insert
    abstract fun insertAirBillScanned(entity: AirbillScannedEntity)

}