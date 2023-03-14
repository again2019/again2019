package com.goingbacking.goingbacking.service

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.goingbacking.goingbacking.util.NotificationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class AlarmService : Service() {
    companion object {
        private const val START_FOREGROUND = 19
        private const val FINISH_FOREGROUND = 20
        private const val STOP_FOREGROUND = 21
        private const val MOVE_ONGROUND = 22

    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action) {
            "FIRST_START_FOREGROUND" -> {
                startForegroundService1()
            }
            "START_FOREGROUND" -> {
                stopSelf(START_FOREGROUND)
                val wakeUpTime = intent.getLongExtra("wakeUpTime", 0L)
                val duration = intent.getLongExtra("duration", 0L)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService2(wakeUpTime, duration)
                }
            }
            "FINISH_FOREGROUND" -> {
                stopSelf(FINISH_FOREGROUND)
                startForegroundService3()
            }
            "STOP_FOREGROUND" -> {
                stopSelf(FINISH_FOREGROUND)
            }
            "STOP_FOREGROUND2" -> {
                stopSelf(STOP_FOREGROUND)

            }
            "MOVE" -> {
                stopForeground(true)
            }
            "this_no_start" -> {
                stopForeground(true)
            }
        }



        return START_STICKY
    }




    @RequiresApi(Build.VERSION_CODES.S)
    private fun startForegroundService1() {
        val notification = NotificationUtil.showTimerReadyNotification(this)
        startForeground(START_FOREGROUND, notification)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startForegroundService2(wakeUpTime :Long, duration : Long) {

        //Log.d("experiment", "wakeup time start")


        val notification = NotificationUtil.createNotification(this@AlarmService, wakeUpTime, duration)
        startForeground(FINISH_FOREGROUND, notification)


    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun startForegroundService3() {
        val notification = NotificationUtil.showTimerExpiredNotification(this)
        startForeground(STOP_FOREGROUND, notification)

    }

    override fun onBind(intent: Intent): IBinder? {
        //바운드 서비스가 아님
        return null
    }


}