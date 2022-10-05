package com.goingbacking.goingbacking

import android.content.Context
import android.os.CountDownTimer
import android.util.Log

class Utils {
    companion object {

        private var timerLengthSeconds = 0L
        var timerState = TimerState.Stopped
        private var secondsRemaining = 0L


        private lateinit var timer: CountDownTimer // 순서대로 얼마나 타이머를 진행할지 나타냄, thread 사용할 필요 없음

        enum class TimerState {
            Stopped, Paused, Running
        }

        fun startTimer(context: Context) {
            timerState = TimerState.Running // timer 상태를 running으로 바꾼다
            timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
                override fun onFinish() = onTimerFinished(context)
                override fun onTick(millisUntilFinished: Long) { // millisUntilFinished: 남아있는 시간, 수행간격 마다 호출
                    secondsRemaining = millisUntilFinished / 1000 // 남아있는 시간  =
                    Log.d("experiment", " -> lengthInMinutes" + secondsRemaining.toString())

                }

            }.start()
        }

        fun onTimerFinished(context: Context) {
            timerState = TimerState.Stopped
            // timerState =  Stop으로 둔다
            setNewTimerLength()
            // 다시 1초로 초기화한다.
            PrefUtil.setSecondsRemaining(timerLengthSeconds, context)
            secondsRemaining = timerLengthSeconds // 남아있는 시간 = 1분(60초)

        }

        fun setNewTimerLength() {
            val lengthInMinutes = 60 //exp1
            // 1을 return 한다.
            Log.d("experiment", " -> lengthInMinutes" + lengthInMinutes.toString())
            timerLengthSeconds = (lengthInMinutes!! * 60L)
            // timeLengthSeconds = 60이 됨
            Log.d("experiment", " -> lengthInMinutes" + timerLengthSeconds.toString())

        }



    }


}