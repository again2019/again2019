package com.goingbacking.goingbacking

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.goingbacking.goingbacking.ViewModel.AlarmViewModel

class AlarmService () : Service() {


    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}