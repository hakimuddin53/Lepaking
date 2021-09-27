package com.example.lepaking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lepaking.LepakingApplication
import com.example.lepaking.common.utility.NumberUtility
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.entity.OrderDetailEntity
import com.example.lepaking.model.ui.OrderDetailModel
import javax.inject.Inject

class OrderViewModel : ViewModel() {

    lateinit var distinctOrderDetailLiveData : LiveData<List<OrderDetailModel>>

    @Inject lateinit var orderDetailDao: OrderDetailDao


    init {
        LepakingApplication.dataComponent.inject(this)
        seedData()
        loadOrderDetails()
    }

    private fun loadOrderDetails(){
        distinctOrderDetailLiveData = orderDetailDao.loadDistinctOrderDetail()
    }

    private fun seedData(){

        orderDetailDao.deleteAllOrderDetails()

        val orderDetail = OrderDetailEntity()
        orderDetail.orderDetailId = NumberUtility.getUUID()
        orderDetail.code = "Cust-312"
        orderDetail.timeReceived = "4:50pm"
        orderDetail.name = "Hakim"
        orderDetail.telephoneNumber = "0176288370"
        orderDetail.orderType = "Dine In"
        orderDetail.pickupTime = "5:00pm"
        orderDetail.totalPrice = "RM 32"
        orderDetail.dishName = "Nasi Goreng Ayam"
        orderDetail.dishQuantity = 2
        orderDetail.dishPrice = "RM 20"
        orderDetail.dishRemark =  "Extra Spicy"
        orderDetailDao.insertOrderDetail(orderDetail)

        orderDetail.orderDetailId = NumberUtility.getUUID()
        orderDetail.code = "Cust-312"
        orderDetail.timeReceived = "4:50pm"
        orderDetail.name = "Hakim"
        orderDetail.telephoneNumber = "0176288370"
        orderDetail.orderType = "Dine In"
        orderDetail.pickupTime = "5:00pm"
        orderDetail.totalPrice = "RM 32"
        orderDetail.dishName = "Nasi Goreng Paprik"
        orderDetail.dishQuantity = 1
        orderDetail.dishPrice = "RM 10"
        orderDetail.dishRemark =  "Extra Spicy"
        orderDetailDao.insertOrderDetail(orderDetail)
    }
}