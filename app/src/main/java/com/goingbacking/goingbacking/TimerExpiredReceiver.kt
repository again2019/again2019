package com.goingbacking.goingbacking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.goingbacking.goingbacking.InputActivityPackage.FirstInputActivity
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {



        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(FirstMainFragment.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
        // stop 상태를 저장하고, 0초를 저장한다.


    }
}