package com.goingbacking.goingbacking.Service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.goingbacking.goingbacking.util.NotificationUtil

class AlarmService () : Service() {
    companion object {
        private const val START_FOREGROUND = 19
        private const val FINISH_FOREGROUND = 20
        private const val STOP_FOREGROUND = 21

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        //Log.d("experiment", "sssssssssssssssss" + intent?.action.toString())
        when(intent?.action) {
            "FIRST_START_FOREGROUND" -> {
                startForegroundService1()
            }
            "START_FOREGROUND" -> {
                stopSelf(START_FOREGROUND)
                val wakeUpTime = intent.getLongExtra("wakeUpTime", 0L)
                //Log.d("experiment", "sssssssssssssssss wakeup time ${wakeUpTime}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService2(wakeUpTime)
                }
            }
            "FINISH_FOREGROUND" -> {
                stopSelf(FINISH_FOREGROUND)
                startForegroundService3()
               // NotificationUtil.showTimerExpired()
            }
            "STOP_FOREGROUND" -> {
                stopSelf(FINISH_FOREGROUND)
            }
            "STOP_FOREGROUND2" -> {
                stopSelf(STOP_FOREGROUND)
            }

        }



        return START_STICKY
    }




    private fun startForegroundService1() {
        val notification = NotificationUtil.showTimerReadyNotification(this)
        startForeground(START_FOREGROUND, notification)
    }


    private fun startForegroundService2(wakeUpTime :Long) {

        //Log.d("experiment", "wakeup time start")
        val notification = NotificationUtil.createNotification(this, wakeUpTime)
        startForeground(FINISH_FOREGROUND, notification)
    }

    private fun startForegroundService3() {
        val notification = NotificationUtil.showTimerExpiredNotification(this)
        startForeground(STOP_FOREGROUND, notification)

    }

    override fun onBind(intent: Intent): IBinder? {
        //바운드 서비스가 아님
        return null
    }


}