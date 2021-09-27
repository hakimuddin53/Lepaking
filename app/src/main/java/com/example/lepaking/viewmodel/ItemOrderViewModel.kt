package com.example.lepaking.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.lepaking.LepakingApplication
import com.example.lepaking.common.eventbus.OrderItemClickBus
import com.example.lepaking.common.extension.onSingleClick
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.entity.OrderDetailEntity
import com.example.lepaking.model.ui.OrderDetailModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ItemOrderViewModel(private val orderDetail: OrderDetailModel)  : ViewModel() {

    @Inject lateinit var orderDetailDao: OrderDetailDao
    init {
        LepakingApplication.dataComponent.inject(this)
    }

    fun getCode() = orderDetail.code

    fun getTimeReceived() = orderDetail.timeReceived

    @ExperimentalCoroutinesApi
    fun onItemOrderClick(view: View) {
        view.onSingleClick {
            OrderItemClickBus.send(orderDetail.code,orderDetail.timeReceived)
        }
    }
}