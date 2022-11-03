package com.goingbacking.goingbacking.BR

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.goingbacking.goingbacking.Service.AlarmService
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.Repository.AlarmRepository
import com.goingbacking.goingbacking.util.TimerUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DoingReceiver : BroadcastReceiver() {
    val alarmRepository = AlarmRepository(FirebaseAuth.getInstance().currentUser, FirebaseFirestore.getInstance())
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
                val intent1 = Intent(context, AlarmService::class.java)
                intent1.action = "FIRST_START_FOREGROUND"
                context.startService(intent1)

                //NotificationUtil.showTimerReady(context)
            }
            AppConstants.ACTION_START -> {
                end_time = PrefUtil.getEndTime(context)

                Log.d("experiment", "end_time ${end_time} id ${id} channel ${type}")
                Log.d("experiment", "end_time ${(end_time) / 60} ${(end_time) % 60}")

                // 시작 시간
                val currentTime = System.currentTimeMillis()
                PrefUtil.setStartTime(currentTime, context)
                // 도착 시간
                val calendar = Calendar.getInstance()
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
                Log.d("experiment", "duration: $duration | ")

                val intent2 = Intent(context, AlarmService::class.java)
                intent2.putExtra("wakeUpTime", wakeUpTime)
                intent2.putExtra("duration", duration)
                intent2.action = "START_FOREGROUND"
                context.startService(intent2)

                TimerUtils.startTimer(context, duration)
            }
            AppConstants.ACTION_STOP -> {
                TimerUtils.pauseTimer()
                currentTime = PrefUtil.getStartTime(context)

                val intent3 = Intent(context, AlarmService::class.java)
                intent3.action = "FINISH_FOREGROUND"
                context.startService(intent3)

                Log.d("experiment",  System.currentTimeMillis().toString())
                Log.d("experiment",  currentTime.toString())

                val current = System.currentTimeMillis()

                val tmpTimeDTO : TmpTimeDTO? = TmpTimeDTO()
                tmpTimeDTO!!.nowSeconds =  current- currentTime
                tmpTimeDTO.startTime = currentTime
                tmpTimeDTO.wakeUpTime = current

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

                alarmRepository.addTmpTimeInfo(tmpTimeDTO)
            }

        }

    }


}