package com.goingbacking.goingbacking.fcm

data class PushNotification (
    val data: NotificationData,
    val to : String,
) {
    data class NotificationData (
        val title :String,
        val body :String,
    )
}

