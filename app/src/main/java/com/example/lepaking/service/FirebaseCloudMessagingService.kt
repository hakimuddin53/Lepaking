package com.example.lepaking.service

import com.example.lepaking.LepakingApplication
import com.example.lepaking.SessionData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import javax.inject.Inject

class FirebaseCloudMessagingService: FirebaseMessagingService()
{
    @Inject lateinit var sessionData: SessionData

    init {
        LepakingApplication.dataComponent.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Timber.i("FCM message received!!")
        // check whether the fcm message is sent to a topic or a not. Sending to topic means it is for apk update, else its for van stock notification
//        if (remoteMessage.from == "/topics/${sessionData.subscribedFCMTopic}") {
//            if (remoteMessage.data.isNotEmpty()) {
//                val data = Gson().fromJson<ApkUpdateDataModel>(remoteMessage.data.toString(), ApkUpdateDataModel::class.java)
//
//                sessionData.apkUpdateId = data.apkUpdateId
//                sessionData.apkUpdateIsActive = data.apkUpdateIsActive
//                sessionData.apkUpdateVersion = data.apkUpdateVersion
//                sessionData.apkUpdateFilename = data.apkUpdateFilename
//                Timber.i("FCM message received!!")
//            }
//
//        } else {
//            if (remoteMessage.data.isNotEmpty()) {
//                val module = remoteMessage.data["module"]
//                val documentId = remoteMessage.data["id"]
//                val documentNumber = remoteMessage.data["number"]
//                val serverDate = remoteMessage.data["date"]
//
//                notificationRepository.createNotification(module!!,documentId!!,documentNumber!!,serverDate!!,module)
//
//                /* when (module) {
//                     "StockTransfer"-> {
//                         notificationRepository.createNotification(module,documentId!!,documentNumber!!,serverDate!!,module)
//                     }
//
//                     "StockAdjustment"-> {
//
//                         notificationRepository.createNotification(module,documentId!!,documentNumber!!,serverDate!!,module)
//
//
//                     }
//                 }*/
//            }
//        }
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