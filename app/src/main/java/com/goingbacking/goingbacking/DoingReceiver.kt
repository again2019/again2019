package com.goingbacking.goingbacking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DoingReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("experiment", "okay doingReceiver")

        when (intent.action){

            AppConstants.ACTION_READY -> {
                NotificationUtil.showTimerReady(context)
            }
            AppConstants.ACTION_START -> {


            }



        }

    }
}