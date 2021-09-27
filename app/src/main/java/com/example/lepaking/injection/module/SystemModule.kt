package com.resmal.smartsales.injection.module

import android.content.Context
import com.example.lepaking.common.provider.SystemInformationProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SystemModule {
    @Provides
    @Singleton
    fun provideSystemInformationProvider(context: Context): SystemInformationProvider
    {
        return SystemInformationProvider(context)
    }
}