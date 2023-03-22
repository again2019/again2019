package com.example.domain.repository

import com.example.domain.model.NotificationModel

interface NotificationRepository {

    suspend fun postNotificationModel(notificationModel: NotificationModel, result: (com.example.domain.util.Response<String>) -> Unit)
}