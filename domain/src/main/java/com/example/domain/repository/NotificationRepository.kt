package com.example.domain.repository

import com.example.domain.model.NotificationModel
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody

interface NotificationRepository {

    suspend fun postNotificationModel(notificationModel: NotificationModel) : ApiResponse<ResponseBody>
}