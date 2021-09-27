package com.example.lepaking.workmanager

import android.content.Context
import android.util.Log
import androidx.work.*
import java.util.*
import java.util.concurrent.TimeUnit


object WorkManagerScheduler {

    fun refreshPeriodicWork1(context: Context) {
        //define constraints
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val refreshCpnWork = PeriodicWorkRequest.Builder(MyWorker::class.java,
            15, TimeUnit.MINUTES)
          //  .setInitialDelay(minutes, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag("myWorkManager1")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager1",
            ExistingPeriodicWorkPolicy.KEEP, refreshCpnWork)
    }

    fun refreshPeriodicWork2(context: Context) {
        //define constraints
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val refreshCpnWork = PeriodicWorkRequest.Builder(MyWorker::class.java,
            15, TimeUnit.MINUTES)
              .setInitialDelay(5, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag("myWorkManager2")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager2",
            ExistingPeriodicWorkPolicy.KEEP, refreshCpnWork)
    }

    fun refreshPeriodicWork3(context: Context) {
        //define constraints
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val refreshCpnWork = PeriodicWorkRequest.Builder(MyWorker::class.java,
            15, TimeUnit.MINUTES)
              .setInitialDelay(10, TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag("myWorkManager3")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager3",
            ExistingPeriodicWorkPolicy.KEEP, refreshCpnWork)
    }
}