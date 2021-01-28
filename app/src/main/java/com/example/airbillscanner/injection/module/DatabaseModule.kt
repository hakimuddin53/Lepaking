package com.example.airbillscanner.injection.module

//import com.google.android.libraries.places.internal.db
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.airbillscanner.model.ApplicationDatabase
import com.example.airbillscanner.model.database.dao.LoginDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by cytan on 2/12/2018.
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



}