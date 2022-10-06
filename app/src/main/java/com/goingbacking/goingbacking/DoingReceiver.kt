package com.goingbacking.goingbacking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DoingReceiver : BroadcastReceiver() {
    var sharedPreferences : SharedPreferences? = null
    var exp1 :Int? = null
    var exp2 : String? = null

    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var tmpTimeDTO : TmpTimeDTO? = null


    override fun onReceive(context: Context, intent: Intent) {
        Log.d("experiment", "okay doingReceiver")

        init()
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

                PrefUtil.setSecondsRemaining(System.currentTimeMillis(), context)
                Log.d("experiment", "currentTime: " + System.currentTimeMillis())
                Log.d("experiment", "wakeupTime: $wakeUpTime")
                Log.d("experiment", "secondsRemaining: $secondsRemaining")

                NotificationUtil.showTimerRunning(context, wakeUpTime)
                Utils.startTimer(context, exp1!!)

            }
            AppConstants.ACTION_STOP -> {
                Utils.pauseTimer()
                Log.d("experiment",  System.currentTimeMillis().toString())
                Log.d("experiment",  PrefUtil.getSecondsRemaining(context).toString())

                var current = System.currentTimeMillis()

                tmpTimeDTO!!.nowSeconds =  current- PrefUtil.getSecondsRemaining(context)
                tmpTimeDTO!!.startTime = PrefUtil.getSecondsRemaining(context)
                tmpTimeDTO!!.wakeUpTime = current

                val df = SimpleDateFormat("HH:mm:ss")

                Log.d("experiment", "total: " + (current - PrefUtil.getSecondsRemaining(context)).toString())
                Log.d("experiment", "wakeupTime: " + PrefUtil.getSecondsRemaining(context).toString())
                Log.d("experiment", "currentTime: " + current)

                val total = df.format(Date((current - PrefUtil.getSecondsRemaining(context))))
                val wakeUpTime = df.format(Date(PrefUtil.getSecondsRemaining(context)))
                val currentTime = df.format(Date(current))

                Log.d("experiment", "total: " +total.toString())
                Log.d("experiment", "wakeupTime: " + wakeUpTime.toString())
                Log.d("experiment", "currentTime: " + currentTime.toString())

                firebaseFirestore?.collection("TmpTimeInfo")?.document(userId!!)?.collection(userId!!)?.add(tmpTimeDTO!!)



            }




        }

    }

    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        tmpTimeDTO = TmpTimeDTO()
    }
}