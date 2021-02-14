package com.example.airbillscanner.injection.module

//import com.google.android.libraries.places.internal.db
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.airbillscanner.model.ApplicationDatabase
import com.example.airbillscanner.model.database.dao.AirbillScannedDao
import com.example.airbillscanner.model.database.dao.CourierDao
import com.example.airbillscanner.model.database.dao.LoginDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by hakim on 2/12/2021.
 */
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): ApplicationDatabase = Room
        .databaseBuilder(application as Context, ApplicationDatabase::class.java, "airbillscanner.db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideLoginDao(database: ApplicationDatabase): LoginDao = database.loginDao()

    @Provides
    @Singleton
    fun provideCourierDao(database: ApplicationDatabase): CourierDao = database.courierDao()

    @Provides
    @Singleton
    fun provideAirBillScannerDao(database: ApplicationDatabase): AirbillScannedDao = database.airbillScannedDao()
}