package com.example.airbillscanner.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airbillscanner.AirbillScannerApplication
import com.example.airbillscanner.common.extension.getNonNull
import com.example.airbillscanner.common.extension.onSingleClick
import com.example.airbillscanner.model.database.dao.AirbillScannedDao
import com.example.airbillscanner.model.database.dao.CourierDao
import com.example.airbillscanner.model.database.entity.AirbillScannedEntity
import com.example.airbillscanner.model.database.entity.CourierEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi

import javax.inject.Inject

class AirBillViewModel : ViewModel() {

    val valueBarcode: ObservableField<String> = ObservableField()
    val valueOrderNumber: ObservableField<String> = ObservableField()
    val couriers: LiveData<List<CourierEntity>>
    val barcodeChangesClicked = ObservableField<Any>()
    val uploadGoogleDrivesClicked = ObservableField<Any>()

    var selectedCourierId = MutableLiveData(0)

    val lastConsignmentScanned: ObservableField<String> = ObservableField("Last Scanned : ")

    @Inject lateinit var courierDao: CourierDao
    @Inject lateinit var airbillScannedDao: AirbillScannedDao


    init {
        AirbillScannerApplication.dataComponent.inject(this)

        courierDao.deleteCouriers()

        val courier = CourierEntity()
        courier.Id = 1
        courier.code = "POS"
        courier.description = "POSLAJU"
        courierDao.insertCouriers(courier)

        courier.Id = 2
        courier.code = "DHL"
        courier.description = "DHL"
        courierDao.insertCouriers(courier)

        courier.Id = 3
        courier.code = "JT"
        courier.description = "J & T"
        courierDao.insertCouriers(courier)

        courier.Id = 4
        courier.code = "NIN"
        courier.description = "NINJA Van"
        courierDao.insertCouriers(courier)

        courier.Id = 5
        courier.code = "SHP"
        courier.description = "SHOPEE EXPRESS"
        courierDao.insertCouriers(courier)

        courier.Id = 6
        courier.code = "CL"
        courier.description = "CITY LINK"
        courierDao.insertCouriers(courier)

        courier.Id = 7
        courier.code = "PG"
        courier.description = "PIGEON"
        courierDao.insertCouriers(courier)

        couriers = courierDao.loadCouriers()
    }

    @ExperimentalCoroutinesApi
    fun onBarcodeButtonClick(view: View) {
        view.onSingleClick {
            barcodeChangesClicked.notifyChange()
        }
    }

    @ExperimentalCoroutinesApi
    fun onUploadGoogleDriveClick(view: View) {
        view.onSingleClick {
            uploadGoogleDrivesClicked.notifyChange()
        }
    }

    fun insertScannedBarcode()
    {
        val airbillScanned = AirbillScannedEntity()

        airbillScanned.barcode = valueBarcode.getNonNull()
        airbillScanned.courierId =  selectedCourierId.value!!
        airbillScanned.orderNumber = valueOrderNumber.getNonNull()
        airbillScannedDao.insertAirBillScanned(airbillScanned)
    }

    fun areRequiredFieldsFilled() : Boolean {
        return when {
            selectedCourierId.value == 0 -> false
            valueBarcode.getNonNull().trim().isEmpty() -> false
            else -> true
        }
    }
}