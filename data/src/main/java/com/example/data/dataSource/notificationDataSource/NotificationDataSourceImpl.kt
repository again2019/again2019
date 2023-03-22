package com.example.data.dataSource.notificationDataSource

import android.util.Log
import com.example.data.api.NotificationAPI
import com.example.data.entity.NotificationEntity
import com.example.domain.util.Response

import com.google.firebase.messaging.SendException
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONException


class NotificationDataSourceImpl (
    private val api: NotificationAPI,
) : NotificationDataSource {

    override suspend fun postNotificationEntity(notificationEntity: NotificationEntity) : ApiResponse<ResponseBody> {
        return api.postNotification(notificationEntity)
    }
}