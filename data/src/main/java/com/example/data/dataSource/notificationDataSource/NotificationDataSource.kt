package com.example.data.dataSource.notificationDataSource


import com.example.data.entity.NotificationEntity
import okhttp3.ResponseBody
import retrofit2.Response

interface NotificationDataSource {
    suspend fun postNotificationEntity(notificationEntity: NotificationEntity) : Response<ResponseBody>

}