package com.example.data.dataSource.notificationDataSource


import com.example.data.entity.NotificationEntity
import com.example.domain.util.Response
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody

interface NotificationDataSource {
    suspend fun postNotificationEntity(notificationEntity: NotificationEntity) : ApiResponse<ResponseBody>

}