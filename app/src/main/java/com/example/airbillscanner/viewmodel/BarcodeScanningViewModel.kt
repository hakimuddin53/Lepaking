package com.example.airbillscanner.viewmodel

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.airbillscanner.AirbillScannerApplication
import com.example.airbillscanner.R
import com.example.airbillscanner.common.provider.ResourceProvider
import javax.inject.Inject

class BarcodeScanningViewModel : ViewModel() {

    @Inject lateinit var resProvider: ResourceProvider

    val barcode = ObservableField<String>()
    val isCameraFacingFront = ObservableBoolean()
    val cameraTintColor = ObservableField<Int>()
    val confirmClicked = ObservableField<Any>()

    init {
        AirbillScannerApplication.dataComponent.inject(this)
        cameraTintColor.set(resProvider.getColor(R.color.white))
    }

    fun onCameraToggleClick(view: View)
    {
        isCameraFacingFront.set(!isCameraFacingFront.get())
        if(isCameraFacingFront.get())
        {
            cameraTintColor.set(resProvider.getColor(R.color.majesticWhiteAccent))
        }else{
            cameraTintColor.set(resProvider.getColor(R.color.white))
        }
    }

    fun onConfirmClick(view: View)
    {
        confirmClicked.notifyChange()
    }
}