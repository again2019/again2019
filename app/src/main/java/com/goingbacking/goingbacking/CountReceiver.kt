package com.goingbacking.goingbacking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CountReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "count receiverstart",Toast.LENGTH_SHORT).show()

        when (intent.action){
            AppConstants.ACTION_READY -> {
                NotificationUtil.showTimerReady(context)
            }

            AppConstants.ACTION_START -> {
            



            }
        }

    }
}