package com.goingbacking.goingbacking.Service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.goingbacking.goingbacking.util.NotificationUtil

class AlarmService () : Service() {


    override fun onCreate() {
        super.onCreate()


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("experiment", "sssssssssssssssss" + intent?.action.toString())


        when(intent?.action) {
            "FIRST_START_FOREGROUND" -> {
                startForegroundService1()
            }


            "START_FOREGROUND" -> {
                stopSelf(19)
                val wakeUpTime = intent.getLongExtra("wakeUpTime", 0L)
                Log.d("experiment", "sssssssssssssssss wakeup time ${wakeUpTime}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService2(wakeUpTime)
                }
            }
            "FINISH_FOREGROUND" -> {
                stopSelf(20)
                startForegroundService3()
               // NotificationUtil.showTimerExpired()
            }
            "STOP_FOREGROUND" -> {
                stopSelf(20)
            }
            "STOP_FOREGROUND2" -> {
                stopSelf(21)
            }

        }



        return START_STICKY
    }




    private fun startForegroundService1() {
        val notification = NotificationUtil.showTimerReadyNotification(this)
        startForeground(19, notification)
    }


    private fun startForegroundService2(wakeUpTime :Long) {

        Log.d("experiment", "wakeup time start")
        val notification = NotificationUtil.createNotification(this, wakeUpTime)
        startForeground(20, notification)
    }

    private fun startForegroundService3() {
        val notification = NotificationUtil.showTimerExpiredNotification(this)
        startForeground(21, notification)

    }

    override fun onBind(intent: Intent): IBinder? {
        //바운드 서비스가 아님
        return null
    }


}