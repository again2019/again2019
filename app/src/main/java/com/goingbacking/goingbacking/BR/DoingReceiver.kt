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
import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepository
import com.goingbacking.goingbacking.util.Constants.Companion.CHANNEL
import com.goingbacking.goingbacking.util.Constants.Companion.CURRENTTIME
import com.goingbacking.goingbacking.util.Constants.Companion.DURATION2
import com.goingbacking.goingbacking.util.Constants.Companion.END_TIME
import com.goingbacking.goingbacking.util.Constants.Companion.FINISH_FOREGROUND
import com.goingbacking.goingbacking.util.Constants.Companion.FIRST_START_FOREGROUND
import com.goingbacking.goingbacking.util.Constants.Companion.ID
import com.goingbacking.goingbacking.util.Constants.Companion.START_FOREGROUND
import com.goingbacking.goingbacking.util.Constants.Companion.WAKEUPTIME
import com.goingbacking.goingbacking.util.TimerUtils
import com.goingbacking.goingbacking.util.calendarAlarm
import java.text.SimpleDateFormat
import java.util.*

class DoingReceiver : BroadcastReceiver() {
    val alarmRepository = AlarmRepository()
    var end_time = 0
    var id = 0
    var type = ""
    var currentTime = 0L
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("experiment", "okay doingReceiver")

        id = intent.getIntExtra(ID, 0)
        type = intent.getStringExtra(CHANNEL).toString()
        currentTime = intent.getLongExtra(CURRENTTIME, 0)
        Log.d("experiment", "end_time ${end_time} id ${id} channel ${type}")

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancelAll()
        when (intent.action){

            AppConstants.ACTION_READY -> {
                end_time = intent.getIntExtra(END_TIME, 0)
                PrefUtil.setEndTime(end_time, context)
                val intent1 = Intent(context, AlarmService::class.java)
                intent1.action = FIRST_START_FOREGROUND
                context.startService(intent1)
            }
            AppConstants.ACTION_START -> {
                end_time = PrefUtil.getEndTime(context)

                Log.d("experiment", "end_time ${end_time} id ${id} channel ${type}")
                Log.d("experiment", "end_time ${(end_time) / 60} ${(end_time) % 60}")

                // 시작 시간
                val currentTime = System.currentTimeMillis()
                PrefUtil.setStartTime(currentTime, context)
                // 도착 시간
                val calendar = calendarAlarm(
                    hour = (end_time) / 60,
                    minute = (end_time) % 60,
                    second = 0,
                    millisecond = 0
                )

                val wakeUpTime = calendar.timeInMillis
                // duration (도착 - 시작)
                val duration = wakeUpTime - currentTime

                Log.d("experiment", "currentTime: ${currentTime} | ${SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(Date(currentTime)) } |")
                Log.d("experiment", "wakeupTime: $wakeUpTime | ${SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA).format(Date(wakeUpTime))} ")
                Log.d("experiment", "duration: $duration | ")

                val intent2 = Intent(context, AlarmService::class.java)
                intent2.putExtra(WAKEUPTIME, wakeUpTime)
                intent2.putExtra(DURATION2, duration)
                intent2.action = START_FOREGROUND
                context.startService(intent2)

                TimerUtils.startTimer(context, duration)
            }
            AppConstants.ACTION_STOP -> {
                TimerUtils.pauseTimer()
                val start_currentTime = PrefUtil.getSecondsRemaining(context)

                val intent3 = Intent(context, AlarmService::class.java)
                intent3.action = FINISH_FOREGROUND
                context.startService(intent3)

                Log.d("experiment",  System.currentTimeMillis().toString())
                Log.d("experiment",  start_currentTime.toString())

                val current = System.currentTimeMillis()
                val tmpTimeDTO = TmpTimeDTO(
                    nowSeconds =  (current- start_currentTime) / 60000,
                    startTime = start_currentTime,
                    wakeUpTime = current
                )

                val df = SimpleDateFormat("HH:mm:ss", Locale.KOREA)

                Log.d("experiment", "total: " + (current- start_currentTime).toString())
                Log.d("experiment", "wakeupTime: " + start_currentTime.toString())
                Log.d("experiment", "currentTime: " + current)

                val total = df.format(Date((current- start_currentTime)))
                val wakeUpTime = df.format(Date(start_currentTime))
                val currentTime = df.format(Date(current))

                Log.d("experiment", "total: " +total.toString())
                Log.d("experiment", "wakeupTime: " + wakeUpTime.toString())
                Log.d("experiment", "currentTime: " + currentTime.toString())

                alarmRepository.addTmpTimeInfo(start_currentTime.toString(), tmpTimeDTO)
            }
            "MOVE" -> {
                val intent4 = Intent(context, AlarmService::class.java)
                intent4.action = "MOVE"
                context.startService(intent4)
            }

        }

    }


}