package com.example.data.datasource.notificationDataSource


import com.example.data.entity.NotificationEntity
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody

interface NotificationDataSource {
    suspend fun postNotificationEntity(notificationEntity: NotificationEntity) : ApiResponse<ResponseBody>

}