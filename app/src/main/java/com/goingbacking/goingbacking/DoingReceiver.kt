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
import com.goingbacking.goingbacking.Repository.AlarmRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DoingReceiver : BroadcastReceiver() {
    val alarmRepository = AlarmRepository(FirebaseAuth.getInstance().currentUser, FirebaseFirestore.getInstance())

    var tmpTimeDTO : TmpTimeDTO? = null
    var end_time = 0
    var id = 0
    var type = ""
    var currentTime = 0L
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("experiment", "okay doingReceiver")

        id = intent.getIntExtra("id", 0)
        type = intent.getStringExtra("channel").toString()
        currentTime = intent.getLongExtra("currentTime", 0)
        Log.d("experiment", "end_time ${end_time} id ${id} channel ${type}")

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()
        when (intent.action){

            AppConstants.ACTION_READY -> {
                end_time = intent.getIntExtra("end_time", 0)
                PrefUtil.setEndTime(end_time, context)
                NotificationUtil.showTimerReady(context)
            }
            AppConstants.ACTION_START -> {
                end_time = PrefUtil.getEndTime(context)

                Log.d("experiment", "end_time ${end_time} id ${id} channel ${type}")
                Log.d("experiment", "end_time ${(end_time) / 60} ${(end_time) % 60}")

                // 시작 시간
                val currentTime = System.currentTimeMillis()
                PrefUtil.setStartTime(currentTime, context)
                // 도착 시간
                var calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                calendar.set(Calendar.HOUR, (end_time) / 60)
                calendar.set(Calendar.MINUTE, (end_time) % 60)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val wakeUpTime = calendar.timeInMillis
                // duration (도착 - 시작)
                val duration = wakeUpTime - currentTime

                Log.d("experiment", "currentTime: ${currentTime} | ${SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(currentTime)) } |")
                Log.d("experiment", "wakeupTime: $wakeUpTime | ${SimpleDateFormat("yyyy.MM.dd HH:mm").format(Date(wakeUpTime))} ")
                Log.d("experiment", "duration: $duration | ${SimpleDateFormat("mm").format(duration)}")


                NotificationUtil.showTimerRunning(context, wakeUpTime)
                Utils.startTimer(context, duration)
            }
            AppConstants.ACTION_STOP -> {
                Utils.pauseTimer()
                currentTime = PrefUtil.getStartTime(context)

                Log.d("experiment",  System.currentTimeMillis().toString())
                Log.d("experiment",  currentTime.toString())

                var current = System.currentTimeMillis()

                tmpTimeDTO!!.nowSeconds =  current- currentTime
                tmpTimeDTO!!.startTime = currentTime
                tmpTimeDTO!!.wakeUpTime = current

                val df = SimpleDateFormat("HH:mm:ss")

                Log.d("experiment", "total: " + (current- currentTime).toString())
                Log.d("experiment", "wakeupTime: " + currentTime.toString())
                Log.d("experiment", "currentTime: " + current)

                val total = df.format(Date((current- currentTime)))
                val wakeUpTime = df.format(Date(currentTime))
                val currentTime = df.format(Date(current))

                Log.d("experiment", "total: " +total.toString())
                Log.d("experiment", "wakeupTime: " + wakeUpTime.toString())
                Log.d("experiment", "currentTime: " + currentTime.toString())

                //firebaseFirestore?.collection("TmpTimeInfo")?.document(userId!!)?.collection(userId!!)?.add(tmpTimeDTO!!)
                alarmRepository.addTmpTimeInfo(tmpTimeDTO!!)
            }

        }

    }


}