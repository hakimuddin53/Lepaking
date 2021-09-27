package com.example.lepaking

import android.app.Application
import com.example.lepaking.injection.component.ApplicationComponent
import com.example.lepaking.injection.component.DaggerApplicationComponent
import com.example.lepaking.injection.module.ApplicationModule
import com.jakewharton.threetenabp.AndroidThreeTen

class LepakingApplication: Application()
{

    companion object {
        lateinit var dataComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)


        dataComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

//        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())

    }
}