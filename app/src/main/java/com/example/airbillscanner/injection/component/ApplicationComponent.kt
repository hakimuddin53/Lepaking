package com.example.airbillscanner.injection.component


import com.example.airbillscanner.AirbillScannerApplication
import com.example.airbillscanner.injection.module.ApplicationModule
import com.example.airbillscanner.injection.module.DatabaseModule
import com.example.airbillscanner.injection.module.NetworkModule
import com.example.airbillscanner.ui.login.LoginActivity
import com.example.airbillscanner.view.fragment.AirBillFragment
import com.example.airbillscanner.viewmodel.AirBillViewModel
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
    fun inject(barcodeScanningViewModel: BarcodeScanningViewModel)
    fun inject(airBillViewModel: AirBillViewModel)

    fun inject(loginActivity: LoginActivity)
    fun inject(airBillFragment: AirBillFragment)
}


