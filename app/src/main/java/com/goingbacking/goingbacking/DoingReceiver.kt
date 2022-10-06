package com.goingbacking.goingbacking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment
import java.util.*

class DoingReceiver : BroadcastReceiver() {
    var sharedPreferences : SharedPreferences? = null
    var exp1 :Int? = null
    var exp2 : String? = null
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("experiment", "okay doingReceiver")

        when (intent.action){

            AppConstants.ACTION_READY -> {
                NotificationUtil.showTimerReady(context)
            }
            AppConstants.ACTION_START -> {
                sharedPreferences = context.getSharedPreferences("time", AppCompatActivity.MODE_PRIVATE)
                exp1 = sharedPreferences!!.getInt("TodayTime", 10)

                val minutesRemaining = exp1!!
                val secondsRemaining = minutesRemaining!! * 1L

                var calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                calendar.add(Calendar.SECOND, secondsRemaining.toInt())
                val wakeUpTime = calendar.timeInMillis
                Log.d("experiment", "wakeupTime: $wakeUpTime")
                Log.d("experiment", "secondsRemaining: $secondsRemaining")

                NotificationUtil.showTimerRunning(context, wakeUpTime)
                Utils.startTimer(context, exp1!!)







            }



        }

    }
}