package com.example.lepaking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.lepaking.LepakingApplication
import com.example.lepaking.common.utility.NumberUtility
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.entity.OrderDetailEntity
import com.example.lepaking.model.database.entity.OrderEntity
import javax.inject.Inject

class OrderViewModel : ViewModel() {

    lateinit var orderLiveData : LiveData<List<OrderEntity>>

    @Inject lateinit var orderDetailDao: OrderDetailDao


    init {
        LepakingApplication.dataComponent.inject(this)
        seedData()
        loadOrderDetails()
    }

    private fun loadOrderDetails(){
        orderLiveData = orderDetailDao.loadOrders()
    }

    fun getPendingOrderCount() : Int{
        return orderDetailDao.getPendingOrderCount()
    }

    private fun seedData(){
        orderDetailDao.deleteAllOrderDetails()
        orderDetailDao.deleteAllOrders()

        val order = OrderEntity()
        order.orderId = NumberUtility.getUUID()
        order.code = "Cust-312"
        order.timeReceived = "4:50pm"
        order.name = "Hakim"
        order.telephoneNumber = "0176288370"
        order.orderType = "Dine In"
        order.pickupTime = "5:00pm"
        order.totalPrice = "RM 32"
        orderDetailDao.insertOrder(order)

        val orderDetail = OrderDetailEntity()
        orderDetail.orderDetailId = NumberUtility.getUUID()
        orderDetail.orderId = order.orderId
        orderDetail.dishName = "Nasi Goreng Ayam"
        orderDetail.dishQuantity = 2
        orderDetail.dishPrice = "RM 20"
        orderDetail.dishRemark =  "Extra Spicy"
        orderDetailDao.insertOrderDetail(orderDetail)

        orderDetail.orderDetailId = NumberUtility.getUUID()
        orderDetail.orderId = order.orderId
        orderDetail.dishName = "Nasi Goreng Paprik"
        orderDetail.dishQuantity = 1
        orderDetail.dishPrice = "RM 10"
        orderDetail.dishRemark =  "Extra Spicy"
        orderDetailDao.insertOrderDetail(orderDetail)
    }
}