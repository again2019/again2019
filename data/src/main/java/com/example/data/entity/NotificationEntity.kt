package com.example.data.entity

data class NotificationEntity (
    val data: NotificationDataEntity,
    val to : String,
) {
    data class NotificationDataEntity (
        val title :String,
        val body :String,
    )
}

