package com.example.domain.repository

import com.example.domain.model.NotificationModel
import okhttp3.ResponseBody
import retrofit2.Response

interface NotificationRepository {

    suspend fun postNotificationModel(notificationModel: NotificationModel): Response<ResponseBody>
}