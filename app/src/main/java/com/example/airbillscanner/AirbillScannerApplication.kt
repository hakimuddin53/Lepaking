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
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class AirbillScannerApplication: Application()
{
    private val lifecycleListener: SampleLifecycleListener by lazy {
        SampleLifecycleListener()
    }
    private var timerDisposable = CompositeDisposable()
    private val PIN_LOCK_TIMEOUT: Long = 5
    private var pinEnforced = false

    @Inject lateinit var sessionData: SessionData
    private val locationDisposable = CompositeDisposable()

    companion object {
        lateinit var dataComponent: ApplicationComponent
        val isApplicationForeground = ObservableBoolean(false)
    }

    override fun onCreate() {
        super.onCreate()


        // turn off frame rate for now
        /*TinyDancer.create()
            .redFlagPercentage(.1f) // set red indicator for 10%....different from default
            .startingXPosition(200)
            .startingYPosition(600)
            .show(applicationContext)*/

        dataComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

        dataComponent.inject(this)

        setupLifecycleListener()
    }


    //This will observe only the application lifecycle and not the activities tied to it
    //This method help in providing precision in knowing if application went to background
    private fun setupLifecycleListener() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleListener)
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    class SampleLifecycleListener: LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onMoveToForeground() {
            Timber.e("Returning to foreground…")
            AirbillScannerApplication.isApplicationForeground.set(true)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onMoveToBackground() {
            Timber.d("Moving to background…")
            AirbillScannerApplication.isApplicationForeground.set(false)
        }
    }
}