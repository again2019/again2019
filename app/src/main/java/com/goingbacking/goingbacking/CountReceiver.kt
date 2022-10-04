package com.goingbacking.goingbacking

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment
import java.util.*

class CountReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "count receiverstart",Toast.LENGTH_SHORT).show()
        Log.d("experiment", "okay")

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 24)
        calendar.set(Calendar.SECOND, 0)

        var dailyNotify = true

        var pm : PackageManager = context.packageManager
        var alarmIntent  = Intent(context, CountReceiver::class.java)
        var pendingIntent : PendingIntent = PendingIntent.getBroadcast(context, 3001, alarmIntent,
            PendingIntent.FLAG_MUTABLE
        )
        var alarmManager : AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if(dailyNotify) {
            if(alarmManager != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

                }
            }
            // 뷩후 실행되는 리시버

        }


        when (intent.action){
            AppConstants.ACTION_READY -> {
                NotificationUtil.showTimerReady(context)
            }

            AppConstants.ACTION_START -> {



                NotificationUtil.showTimerRunning(context, 50)




            }
        }

    }
}