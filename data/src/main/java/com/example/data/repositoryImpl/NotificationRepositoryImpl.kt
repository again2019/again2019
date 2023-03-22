package com.example.data.repositoryImpl

import com.example.data.dataSource.notificationDataSource.NotificationDataSource
import com.example.data.mapper.NotificationMapper
import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import com.example.domain.util.Response
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import okhttp3.ResponseBody
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override suspend fun postNotificationModel(notificationModel: NotificationModel) : ApiResponse<ResponseBody> {
        return notificationDataSource.postNotificationEntity(
            NotificationMapper.mapperToNotificationEntity(notificationModel)
        )
    }
}