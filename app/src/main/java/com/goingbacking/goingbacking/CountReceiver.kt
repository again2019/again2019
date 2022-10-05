package com.goingbacking.goingbacking

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.ID
import android.util.Log
import android.widget.Toast
import androidx.core.app.*
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment
import java.lang.NullPointerException
import java.time.LocalDateTime
import java.util.*

class CountReceiver : BroadcastReceiver() {
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "count receiverstart",Toast.LENGTH_SHORT).show()
        Log.d("experiment", "okay")

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(intent)
        fireReminder(context, intent)



    }



    private fun createNotificationChannel(intent:Intent) {
        val id = intent.getIntExtra("id", 0)
        val type = intent.getStringExtra("type")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("notificationChannel_$id", "$type", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.run{
                enableVibration(true)
                description = "notification"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun fireReminder(context: Context, intent: Intent) {
        val id = intent.getIntExtra("id", 0)
        val type = intent.getStringExtra("type")
        val isRepeat = intent.getBooleanExtra("repeat", false)
        val dateTime = try {
            intent.getSerializableExtra("time") as LocalDateTime
        } catch(e: NullPointerException) {
            LocalDateTime.now()
        }

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(context, id, contentIntent, PendingIntent.FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(context, "notificationChannel_$id")
            .setSmallIcon(R.mipmap.comeback)
            .setContentTitle("aaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            .setContentText("aaaaaaaaaaaaaaaaaa")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText("aaaaaaaaaaaaaaaaaa"))
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(id, builder.build())
        if(!isRepeat) return
        val interval  = intent.getIntExtra("interval", 1)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextIntent = Intent(context, CountReceiver::class.java)
        nextIntent.putExtra("id", id)
        nextIntent.putExtra("type", type)
        nextIntent.putExtra("repeat", true)
        nextIntent.putExtra("interval", interval)

        val pendingIntent = PendingIntent.getBroadcast(context, id, nextIntent, PendingIntent.FLAG_MUTABLE)
        //val nextDate = dateTime.plusDays(interval.toLong())
        val nextDate = dateTime.plusMinutes(interval.toLong())
        Log.d(
            "experiment",
        "SET:$id | type: $type |time:$nextDate |interval $interval| isRepeat:true|"
        )

        Log.d("experiment", nextDate.hour.toString() + nextDate.minute.toString() + nextDate.second.toString())
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, nextDate.hour)
        calendar.set(Calendar.MINUTE, nextDate.minute)
        calendar.set(Calendar.SECOND, nextDate.second)
        calendar.set(Calendar.MILLISECOND, 0)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis, pendingIntent)


    }



}