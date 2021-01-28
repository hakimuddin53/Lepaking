package com.resmal.smartsales.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.airbillscanner.common.provider.ResourceProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by iChris on 8/3/17.
 */
@Module
class SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application) : SharedPreferences
    {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context) : ResourceProvider
    {
        return ResourceProvider(context)
    }
}