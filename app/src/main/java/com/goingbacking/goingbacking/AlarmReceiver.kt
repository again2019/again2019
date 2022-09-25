package com.goingbacking.goingbacking

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        var notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationIntent = Intent(context, MainActivity::class.java)

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        var pendingI = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)
        var builder :NotificationCompat.Builder = NotificationCompat.Builder(context, "default")

        var channelName = "매일 알람 채널"
        var description = "ssss"
        var importance = NotificationManager.IMPORTANCE_HIGH
        var channel : NotificationChannel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel("default", channelName, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        channel.description = description

        notificationManager.createNotificationChannel(channel)

        builder.setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())

            .setTicker("{Time to watch some cool stuff!}")
            .setContentTitle("상태바 드래그시 보이는 타이틀")
            .setContentText("상태바 드래그시 보이는 서브타이틀")
            .setContentInfo("INFO")
            .setContentIntent(pendingI)
            .setSmallIcon(R.drawable.ic_launcher_background)

        notificationManager.notify(1234, builder.build())
        var nextNotifyTime = Calendar.getInstance()

        nextNotifyTime.add(Calendar.DATE, 1)
        var editor :SharedPreferences.Editor = context.getSharedPreferences("daily alarm", Context.MODE_PRIVATE).edit()
        editor.putLong("nextNotifyTime", nextNotifyTime.timeInMillis)
        editor.apply()

        var currentDateTime = nextNotifyTime.time
        var date_text = SimpleDateFormat("yyyy mm dd EE a hh mm", Locale.getDefault()).format(currentDateTime)
        Toast.makeText(context, date_text.toString(), Toast.LENGTH_SHORT).show()

    }
}