package com.goingbacking.goingbacking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.*
class AlertReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var notificationHelper :NotificationHelper = NotificationHelper(context)
        var nb: NotificationCompat.Builder = notificationHelper.getChannelNOtification()

        notificationHelper.getManager().notify(1, nb.build())
    }
}