package com.example.data.repositoryImpl

import com.example.data.dataSource.notificationDataSource.NotificationDataSource
import com.example.data.mapper.NotificationMapper
import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {
    override suspend fun postNotificationModel(notificationModel: NotificationModel): Response<ResponseBody> {
        return notificationDataSource.postNotificationEntity(
            NotificationMapper.mapperToNotificationEntity(notificationModel)
        )
    }
}