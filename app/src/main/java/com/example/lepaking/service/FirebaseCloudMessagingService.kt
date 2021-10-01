package com.example.lepaking.service

import com.example.lepaking.LepakingApplication
import com.example.lepaking.SessionData
import com.example.lepaking.common.utility.NumberUtility
import com.example.lepaking.model.database.dao.OrderDetailDao
import com.example.lepaking.model.database.entity.OrderDetailEntity
import com.example.lepaking.model.database.entity.OrderEntity
import com.example.lepaking.network.model.CustomerOrderModel
import com.example.lepaking.network.model.FoodOrderModel
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import javax.inject.Inject

class FirebaseCloudMessagingService: FirebaseMessagingService()
{
    @Inject lateinit var sessionData: SessionData
    @Inject lateinit var orderDetailDao: OrderDetailDao

    init {
        LepakingApplication.dataComponent.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.i("FCM message received!!")

        orderDetailDao.deleteAllOrderDetails()
        orderDetailDao.deleteAllOrders()

        if (remoteMessage.data.isNotEmpty()) {
            val customer = Gson().fromJson<List<CustomerOrderModel>>(remoteMessage.data["customer"], object: TypeToken<List<CustomerOrderModel>>(){}.type)
            val food = Gson().fromJson<List<FoodOrderModel>>(remoteMessage.data["food"], object: TypeToken<List<FoodOrderModel>>(){}.type)

            val orderId = NumberUtility.getUUID()
            customer.forEach { item ->
                val order = OrderEntity()
                order.orderId = orderId
                order.code = item.OrderID
                order.timeReceived = item.OrderTime
                order.name = item.CustName
                order.telephoneNumber = item.CustTele
                order.orderType = item.Type
                order.pickupTime = item.OrderTime
                order.totalPrice = "RM " + item.TotalPrice
                orderDetailDao.insertOrder(order)
            }

            food.forEach { foodItem ->
                val orderDetail = OrderDetailEntity()
                orderDetail.orderDetailId = NumberUtility.getUUID()
                orderDetail.orderId = orderId
                orderDetail.dishName = foodItem.foodname
                orderDetail.dishQuantity = foodItem.quantity.toInt()
                orderDetail.dishPrice = "RM " + foodItem.price
                orderDetail.dishRemark =  foodItem.remark
                orderDetailDao.insertOrderDetail(orderDetail)
            }
        }
    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    override fun onSendError(p0: String, p1: Exception) {
        super.onSendError(p0, p1)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sessionData.firebaseInstanceId = token
    }
}