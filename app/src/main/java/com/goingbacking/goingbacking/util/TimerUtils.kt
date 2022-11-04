package com.goingbacking.goingbacking.util

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.util.Log
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Repository.AlarmRepository
import com.goingbacking.goingbacking.Service.AlarmService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class TimerUtils {


    companion object {
        private val alarmRepository = AlarmRepository()
        var timerState = AppConstants.Companion.TimerState.Stopped
        private var secondsRemaining = 0L

        private lateinit var timer: CountDownTimer // 순서대로 얼마나 타이머를 진행할지 나타냄, thread 사용할 필요 없음





        fun startTimer(context: Context, duration: Long) {
            secondsRemaining = duration
            Log.d("experiment", " -> lengthInMinutes" + secondsRemaining.toString())
            timerState = AppConstants.Companion.TimerState.Running // timer 상태를 running으로 바꾼다
            timer = object : CountDownTimer(secondsRemaining, 1000) {
                override fun onFinish() = onTimerFinished(context)
                override fun onTick(millisUntilFinished: Long) { // millisUntilFinished: 남아있는 시간, 수행간격 마다 호출
                    secondsRemaining = millisUntilFinished / 1000 // 남아있는 시간  =
                    Log.d("experiment", " -> lengthInMinutes" + secondsRemaining.toString())

                }

            }.start()
        }

        fun pauseTimer() {
            timer.cancel()
        }

        fun onTimerFinished(context: Context) {

            timerState = AppConstants.Companion.TimerState.Stopped
            // timerState =  Stop으로 둔다
            val current = System.currentTimeMillis()

            val tmpTimeDTO = TmpTimeDTO()
            tmpTimeDTO.nowSeconds =  current- PrefUtil.getSecondsRemaining(context)
            tmpTimeDTO.startTime = PrefUtil.getSecondsRemaining(context)
            tmpTimeDTO.wakeUpTime = current

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

            alarmRepository.addTmpTimeInfo(tmpTimeDTO)

            val intent = Intent(context, AlarmService::class.java)
            intent.action = "FINISH_FOREGROUND"
            context.startService(intent)

        }


    }


}