package com.example.lepaking.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lepaking.LepakingApplication
import com.example.lepaking.common.extension.onSingleClick
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.entity.OrderDetailEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi

import javax.inject.Inject

class OrderDetailViewModel(private val orderID: String? = null) : ViewModel() {

    val valueCustomerName: ObservableField<String> = ObservableField()
    val valueTelephone: ObservableField<String> = ObservableField()
    val valueOrderType: ObservableField<String> = ObservableField()
    val valuePickupTime: ObservableField<String> = ObservableField()
    val valueTotalPrice: ObservableField<String> = ObservableField()

    lateinit var orderDetailLiveData : LiveData<List<OrderDetailEntity>>

    @Inject lateinit var orderDetailDao: OrderDetailDao

    init {
        LepakingApplication.dataComponent.inject(this)
        loadOrderDetails()
    }

    private fun loadOrderDetails(){
        orderDetailLiveData = orderDetailDao.loadOrderDetail(orderID)

        val order = orderDetailDao.loadOrder(orderID)

        valueCustomerName.set(order.name)
        valueTelephone.set(order.telephoneNumber)
        valueOrderType.set(order.orderType)
        valuePickupTime.set(order.pickupTime)
        valueTotalPrice.set(order.totalPrice)
    }

    @ExperimentalCoroutinesApi
    fun onPrepareNowClick(view: View) {
        view.onSingleClick {
            orderDetailDao.updatePrepareNowFlag(orderID)
        }
    }
}