package com.goingbacking.goingbacking.BR

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.goingbacking.goingbacking.util.calendar
import java.text.SimpleDateFormat
import java.util.*

class DeviceBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals("android.intent.action.BOOT_COMPLETED")) {
            val alarmIntent = Intent(context, CountReceiver::class.java)
            val pendingIntent :PendingIntent = PendingIntent.getBroadcast(context, 0,
                alarmIntent, FLAG_MUTABLE)

            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager



            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.add(Calendar.MINUTE, 1)

            manager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
            )

        }
    }
}