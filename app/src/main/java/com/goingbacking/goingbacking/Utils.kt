package com.goingbacking.goingbacking

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {

        private var timerLengthSeconds = 0L
        var timerState = TimerState.Stopped
        private var secondsRemaining = 0L

        var auth : FirebaseAuth? = null
        var firebaseFirestore : FirebaseFirestore? = null
        var userId : String? = null
        var tmpTimeDTO : TmpTimeDTO? = null

        private lateinit var timer: CountDownTimer // 순서대로 얼마나 타이머를 진행할지 나타냄, thread 사용할 필요 없음

        enum class TimerState {
            Stopped, Paused, Running
        }



        fun startTimer(context: Context, firstSecondRemaining: Int) {
            secondsRemaining = firstSecondRemaining * 1L
            Log.d("experiment", " -> lengthInMinutes" + secondsRemaining.toString())
            timerState = TimerState.Running // timer 상태를 running으로 바꾼다
            timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
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
            init()

            timerState = TimerState.Stopped
            // timerState =  Stop으로 둔다
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


            NotificationUtil.showTimerExpired(context)


        }

        fun init() {
            auth = FirebaseAuth.getInstance()
            firebaseFirestore = FirebaseFirestore.getInstance()
            userId = auth?.currentUser?.uid
            tmpTimeDTO = TmpTimeDTO()
        }


    }


}