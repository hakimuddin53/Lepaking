package com.example.lepaking.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.lepaking.LepakingApplication
import com.example.lepaking.common.extension.onSingleClick
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.entity.OrderDetailEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ItemOrderDetailViewModel(private val orderDetail: OrderDetailEntity) : ViewModel() {


    @Inject lateinit var orderDetailDao: OrderDetailDao

    init {
        LepakingApplication.dataComponent.inject(this)
    }

    fun getDish() = orderDetail.dishName

    fun getQuantity() = orderDetail.dishQuantity.toString()

    fun getPrice() = orderDetail.dishPrice

    fun getRemark() = orderDetail.dishRemark
}