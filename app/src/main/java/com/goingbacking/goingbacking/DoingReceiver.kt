package com.goingbacking.goingbacking

import android.app.NotificationManager
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

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()
        when (intent.action){

            AppConstants.ACTION_READY -> {
                Log.d("experiment", "end_time ${intent.getIntExtra("end_time", 0)}")
                NotificationUtil.showTimerReady(context, intent.getIntExtra("end_time", 0))
            }
            AppConstants.ACTION_START -> {
                Log.d("experiment", "end_time ${intent.getIntExtra("end_time2", 0)}")

                // 시작 시간
                val currentTime = System.currentTimeMillis()
                // 도착 시간
                var calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                calendar.set(Calendar.HOUR, (intent.getIntExtra("end_time2", 0)) / 60)
                calendar.set(Calendar.MINUTE, (intent.getIntExtra("end_time2", 0)) % 60)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val wakeUpTime = calendar.timeInMillis
                // duration (도착 - 시작)
                val duration = wakeUpTime - currentTime

                Log.d("experiment", "currentTime: ${currentTime} | ${SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm").format(currentTime) } |")
                Log.d("experiment", "wakeupTime: $wakeUpTime | ${SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm").format(wakeUpTime)} ")
                Log.d("experiment", "duration: $duration | ${SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm").format(duration)}")




                NotificationUtil.showTimerRunning(context, duration)

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