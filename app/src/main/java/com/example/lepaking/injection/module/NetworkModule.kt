package com.example.lepaking.injection.module

import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule
{
    @Provides
    @Named("login")
    fun provideRetrofitLogin(gson: Gson, preferences: SharedPreferences): Retrofit
    {
        //val url =   "http://uat.api.mobile.security.smartsales.smartms.biz"
        val url =   "http://dev.api.mobile.security.smartsales.smartms.biz"
        //val url =   "http://uat.cherryice.api.mobile.security.smartsales.smartms.biz"

        return Retrofit.Builder().baseUrl(url).client(getBasicOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    fun getBasicOkHttpClient(): OkHttpClient
    {
        return OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun getGson(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
}