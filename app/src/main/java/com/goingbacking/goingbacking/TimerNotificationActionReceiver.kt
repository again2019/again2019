package com.goingbacking.goingbacking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action){
            AppConstants.ACTION_STOP -> {
                FirstMainFragment.removeAlarm(context) // alarm을 remove해라
                PrefUtil.setTimerState(FirstMainFragment.TimerState.Stopped, context)
                // stop 상태를 임시 저장하기
                NotificationUtil.hideTimerNotification(context)
                // 알림창 없애기


            }
            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = FirstMainFragment.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                FirstMainFragment.removeAlarm(context)
                PrefUtil.setTimerState(FirstMainFragment.TimerState.Paused, context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = FirstMainFragment.setAlarm(context, FirstMainFragment.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(FirstMainFragment.TimerState.Running, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
            AppConstants.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = FirstMainFragment.setAlarm(context, FirstMainFragment.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(FirstMainFragment.TimerState.Running, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}