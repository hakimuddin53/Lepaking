package com.example.lepaking.viewmodel

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lepaking.LepakingApplication
import com.example.lepaking.common.eventbus.OrderItemClickBus
import com.example.lepaking.common.extension.getNonNull
import com.example.lepaking.common.extension.onSingleClick
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.entity.OrderDetailEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi

import javax.inject.Inject

class OrderDetailViewModel(private val code: String? = null) : ViewModel() {

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
        orderDetailLiveData = orderDetailDao.loadDishDetail(code)

        val orderDetail = orderDetailDao.loadOrderDetail(code)

        valueCustomerName.set(orderDetail.name)
        valueTelephone.set(orderDetail.telephoneNumber)
        valueOrderType.set(orderDetail.orderType)
        valuePickupTime.set(orderDetail.pickupTime)
        valueTotalPrice.set(orderDetail.totalPrice)
    }

    @ExperimentalCoroutinesApi
    fun onPrepareNowClick(view: View) {
        view.onSingleClick {
           // OrderItemClickBus.send(orderDetail.code,orderDetail.timeReceived)
        }
    }
}