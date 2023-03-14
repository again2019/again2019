package com.example.domain.model

data class NotificationModel(
    val data: NotificationDataModel,
    val to: String,
) {
    data class NotificationDataModel (
        val title :String,
        val body :String,
    )
}

