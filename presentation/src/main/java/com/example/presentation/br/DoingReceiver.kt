package com.example.presentation.br

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.domain.model.TmpTimeModel
import com.example.domain.usecase.myTmpTime.AddTmpTimeUseCase
import com.example.domain.util.Constants.Companion.ACTION_READY
import com.example.domain.util.Constants.Companion.ACTION_START
import com.example.domain.util.Constants.Companion.ACTION_STOP
import com.example.domain.util.Constants.Companion.ACTION_THIS_NO_START
import com.example.presentation.service.AlarmService
import com.example.domain.util.Constants.Companion.CHANNEL
import com.example.domain.util.Constants.Companion.CURRENTTIME
import com.example.domain.util.Constants.Companion.DURATION2
import com.example.domain.util.Constants.Companion.END_TIME
import com.example.domain.util.Constants.Companion.FINISH_FOREGROUND
import com.example.domain.util.Constants.Companion.FIRST_START_FOREGROUND
import com.example.domain.util.Constants.Companion.ID
import com.example.domain.util.Constants.Companion.SUCCESS
import com.example.domain.util.Constants.Companion.START_FOREGROUND
import com.example.domain.util.Constants.Companion.TAG
import com.example.domain.util.Constants.Companion.WAKEUPTIME
import com.example.domain.util.DatabaseResult
import com.example.domain.util.calendarAlarm
import com.example.domain.util.toast
import com.example.presentation.PrefUtil
import com.example.presentation.TimerUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DoingReceiver @Inject constructor(
    private val addTmpTimeUseCase: AddTmpTimeUseCase,
) : BroadcastReceiver() {
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

            ACTION_READY -> {
                end_time = intent.getIntExtra(END_TIME, 0)
                Log.d("experiment", "end_time ${end_time} ")
                PrefUtil.setEndTime(end_time, context)
                val intent1 = Intent(context, AlarmService::class.java)
                intent1.action = FIRST_START_FOREGROUND
                context.startService(intent1)
            }
            ACTION_START -> {
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
            ACTION_STOP -> {
                TimerUtils.pauseTimer()
                val start_currentTime = PrefUtil.getStartTime(context)

                val intent3 = Intent(context, AlarmService::class.java)
                intent3.action = FINISH_FOREGROUND
                context.startService(intent3)

                Log.d("experiment",  System.currentTimeMillis().toString())
                Log.d("experiment",  start_currentTime.toString())

                val current = System.currentTimeMillis()
                val tmpTimeDTO = TmpTimeModel(
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

                CoroutineScope(Dispatchers.IO).launch {
                    addTmpTimeUseCase(current.toString(), tmpTimeDTO) { result ->
                        when (result) {
                            is DatabaseResult.Success -> {
                                Timber.d("addTmpTimeUseCase: $result.data")
                            }
                            is DatabaseResult.Failure -> {
                                toast(context, "Unknown Failure")
                            }
                        }
                    }
                }

            }
            "MOVE" -> {
                val intent4 = Intent(context, AlarmService::class.java)
                intent4.action = "MOVE"
                Log.d("experiment", "In Doing Receiver")
                context.startService(intent4)
            }
            ACTION_THIS_NO_START -> {
                val intent5 = Intent(context, AlarmService::class.java)
                intent5.action = ACTION_THIS_NO_START
                context.startService(intent5)
            }

        }

    }


}