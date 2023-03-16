package com.example.data.dataSource.notificationDataSource

import com.example.data.api.NotificationAPI
import com.example.data.entity.NotificationEntity
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NotificationDataSourceImpl (
    private val api: NotificationAPI,
) : NotificationDataSource {
    override suspend fun postNotificationEntity(notificationEntity: NotificationEntity): Response<ResponseBody> {
        return api.postNotification(notificationEntity)
    }

}