package com.example.lepaking.injection.component


import com.example.lepaking.injection.module.ApplicationModule
import com.example.lepaking.injection.module.DatabaseModule
import com.example.lepaking.injection.module.NetworkModule
import com.example.lepaking.service.FirebaseCloudMessagingService
import com.example.lepaking.ui.login.LoginActivity
import com.example.lepaking.view.activity.MainActivity
import com.example.lepaking.view.fragment.OrderDetailFragment
import com.example.lepaking.view.fragment.OrderFragment
import com.example.lepaking.viewmodel.ItemOrderDetailViewModel
import com.example.lepaking.viewmodel.OrderDetailViewModel
import com.example.lepaking.viewmodel.ItemOrderViewModel
import com.example.lepaking.viewmodel.OrderViewModel
import com.resmal.smartsales.injection.module.*

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    SharedPreferencesModule::class,
    DatabaseModule::class,
    NetworkModule::class,
    SystemModule::class])

interface ApplicationComponent {
    fun inject(orderDetailViewModel: OrderDetailViewModel)
    fun inject(itemOrderViewModel: ItemOrderViewModel)
    fun inject(itemOrderDetailViewModel: ItemOrderDetailViewModel)
    fun inject(orderViewModel: OrderViewModel)
    fun inject(loginActivity: LoginActivity)
    fun inject(orderDetailFragment: OrderDetailFragment)
    fun inject(orderFragment: OrderFragment)
    fun inject(firebaseCloudMessagingService: FirebaseCloudMessagingService)
    fun inject(mainActivity: MainActivity)



}


