package com.example.airbillscanner

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.airbillscanner.injection.component.ApplicationComponent
import com.example.airbillscanner.injection.component.DaggerApplicationComponent
import com.example.airbillscanner.injection.module.ApplicationModule
import com.jakewharton.threetenabp.AndroidThreeTen
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class AirbillScannerApplication: Application()
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