package com.example.airbillscanner.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airbillscanner.AirbillScannerApplication

import javax.inject.Inject

class AirBillViewModel : ViewModel() {


    var isBarcodeVerify = MutableLiveData<Boolean>()
    val valueBarcode: ObservableField<String> = ObservableField()

    init {
        AirbillScannerApplication.dataComponent.inject(this)
    }
}