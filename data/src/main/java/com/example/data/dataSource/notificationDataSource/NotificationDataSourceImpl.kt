package com.example.data.dataSource.notificationDataSource

import com.example.data.api.NotificationAPI
import com.example.data.entity.NotificationEntity
import com.example.domain.util.Response

import com.google.firebase.messaging.SendException




class NotificationDataSourceImpl (
    private val api: NotificationAPI,
) : NotificationDataSource {

    override suspend fun postNotificationEntity(notificationEntity: NotificationEntity, result: (Response<String>) -> Unit) {
        try {
            if(api.postNotification(notificationEntity).isSuccessful) {
                result(Response.Success("Successs"))
            }
        } catch (sendException : SendException) {
            result(Response.Failure(sendException.message!!))
        }
    }
}