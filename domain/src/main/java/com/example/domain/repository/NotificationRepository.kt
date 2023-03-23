package com.example.domain.repository

import androidx.annotation.WorkerThread
import com.example.domain.model.NotificationModel
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface NotificationRepository {

    @WorkerThread
    suspend fun postNotificationModel(
        notificationModel: NotificationModel,
        onError: (String?) -> Unit,
    ) : Flow<String>
}