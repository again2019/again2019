package com.example.data.datasource.notificationDataSource

import com.example.data.api.NotificationAPI
import com.example.data.entity.NotificationEntity

import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody


class NotificationDataSourceImpl (
    private val api: NotificationAPI,
) : NotificationDataSource {

    override suspend fun postNotificationEntity(notificationEntity: NotificationEntity) : ApiResponse<ResponseBody> {
        return api.postNotification(notificationEntity)
    }
}