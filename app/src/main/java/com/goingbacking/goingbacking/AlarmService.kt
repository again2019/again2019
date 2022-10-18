package com.goingbacking.goingbacking

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.goingbacking.goingbacking.ViewModel.AlarmViewModel

class AlarmService () : Service() {


    override fun onCreate() {
        super.onCreate()


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d("experiment", "sssssssssssssssss" + intent?.action.toString())


        when(intent?.action) {
            "START_FOREGROUND" -> {
                val wakeUpTime = intent.getLongExtra("wakeUpTime", 0L)
                Log.d("experiment", "sssssssssssssssss wakeup time ${wakeUpTime}")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(wakeUpTime)
                }
            }
            "STOP_FOREGROUND" -> {
                stopForegroundService()
            }
        }



        return START_STICKY
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }

    private fun startForegroundService(wakeUpTime :Long) {

        Log.d("experiment", "wakeup time start")
        val notification = NotificationUtil.createNotification(this, wakeUpTime)
        startForeground(20, notification)
    }

    override fun onBind(intent: Intent): IBinder? {
        //바운드 서비스가 아님
        return null
    }


}