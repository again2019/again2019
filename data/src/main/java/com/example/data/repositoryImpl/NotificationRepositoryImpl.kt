package com.example.data.repositoryImpl

import com.example.data.datasource.notificationDataSource.NotificationDataSource
import com.example.data.mapper.NotificationMapper
import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import com.skydoves.sandwich.*

import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDataSource: NotificationDataSource
) : NotificationRepository {


    override suspend fun postNotificationModel(
        notificationModel: NotificationModel,
        onError: (String?) -> Unit,
    ) = flow {

        val response = notificationDataSource.postNotificationEntity(
            NotificationMapper.mapperToNotificationEntity(notificationModel)
        )

        response.suspendOnSuccess {
            emit("success")
        }.onError {
            onError(message())
        }.onException {
            onError(message)
        }
    }
}