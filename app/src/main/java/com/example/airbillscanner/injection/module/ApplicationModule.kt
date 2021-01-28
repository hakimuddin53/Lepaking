package com.example.airbillscanner.injection.module

import android.app.Application
import android.content.Context
import com.example.airbillscanner.AirbillScannerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by iChris on 7/24/17.
 */
@Module
class ApplicationModule(val application : AirbillScannerApplication)
{
    @Provides
    @Singleton
    fun provideContext() : Context = application

    @Provides
    @Singleton
    fun provideApplication() : Application = application

    @Provides
    @Singleton
    fun provideSmartSalesApplication() : AirbillScannerApplication = application
}