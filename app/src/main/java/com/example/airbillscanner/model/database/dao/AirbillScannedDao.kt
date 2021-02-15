package com.example.airbillscanner.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.airbillscanner.model.database.entity.AirbillScannedEntity
import com.example.airbillscanner.model.database.entity.CourierEntity
import com.example.airbillscanner.model.database.entity.LoginEntity
import com.example.airbillscanner.model.ui.AirbillScannedModel


@Dao
abstract class AirbillScannedDao {

    @Query("SELECT A.barcode,A.orderNumber,C.description,A.createdDate   FROM AirbillScanned A JOIN Courier C ON A.courierId = C.Id where  strftime('%Y-%m-%d', A.createdDate) = :date  ")
    abstract fun loadAirBillScanned(date : String): List<AirbillScannedModel>


    @Query("DELETE FROM AirbillScanned")
    abstract fun deleteAirBillScanned()

    @Insert
    abstract fun insertAirBillScanned(entity: AirbillScannedEntity)

}