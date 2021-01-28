package com.example.airbillscanner.injection.component


import com.example.airbillscanner.AirbillScannerApplication
import com.example.airbillscanner.injection.module.ApplicationModule
import com.example.airbillscanner.injection.module.DatabaseModule
import com.example.airbillscanner.injection.module.NetworkModule
import com.example.airbillscanner.viewmodel.BarcodeScanningViewModel
import com.resmal.smartsales.injection.module.*

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    SharedPreferencesModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    SystemModule::class])

interface ApplicationComponent {
    fun inject(airbillScannerApplication: AirbillScannerApplication)
    fun inject(barcodeScanningViewModel: BarcodeScanningViewModel)


}


