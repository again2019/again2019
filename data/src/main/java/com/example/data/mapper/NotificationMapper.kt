package com.example.data.mapper

import com.example.data.entity.NotificationEntity
import com.example.domain.model.NotificationModel

object NotificationMapper {
    private fun mapperToNotificationDataModel(notificationData: NotificationEntity.NotificationDataEntity) : NotificationModel.NotificationDataModel {
        return NotificationModel.NotificationDataModel(
            title = notificationData.title,
            body = notificationData.body,
        )
    }

    private fun mapperToNotificationDataEntity(notificationData: NotificationModel.NotificationDataModel) : NotificationEntity.NotificationDataEntity {
        return NotificationEntity.NotificationDataEntity(
            title = notificationData.title,
            body = notificationData.body,
        )
    }

    fun mapperToNotificationModel(notificationEntity: NotificationEntity) : NotificationModel {
        return NotificationModel(
            data = mapperToNotificationDataModel(notificationEntity.data),
            to = notificationEntity.to
        )
    }

    fun mapperToNotificationEntity(notificationModel: NotificationModel) : NotificationEntity {
        return NotificationEntity(
            data = mapperToNotificationDataEntity(notificationModel.data),
            to = notificationModel.to
        )
    }
}