package com.goingbacking.goingbacking.MainActivityPackage

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.goingbacking.goingbacking.*
import com.goingbacking.goingbacking.Adapter.PagerAdapter
import kotlinx.android.synthetic.main.fragment_first_main.*
import kotlinx.android.synthetic.main.fragment_first_main.view.*
import java.util.*
import java.util.concurrent.TimeUnit

class FirstMainFragment : Fragment() {

    companion object {

        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining:Long) : Long {
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000 // 끝이나는 시간
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            // PendingIntent: 알림이 울렸을 때 수행한 작업을 명시하며 receiver를 사용함
            // 특정 시간 이후에 수행이 됨 -> 특정 시간 이후에 TimerExpiredReciever가 실행됨
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context) {
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_MUTABLE)
            //당장 수행하는 것이 아니라 특정 시점에 수행 (특정 시점: 어플을 사용하고 있지 않을 때)
            // 그냥 Intent로 구현하는 경우 다른 앱에서부터 내가 정의한 Intent를 실행한다는 것인데 이는 불가능하다

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            // AlarmManager: 지정된 시간에 이벤트를 발생시키는 기능,
            // AlarmManager는 안드로이드에서 Alarm을 수행하는 것으로 AlarmManager에게 알림기능을 부탁하여 수행한다.
            // 어떤 Receiver를 호출할지에 대한 정보를 가진 intent를 가지고 있는 PendingIntent와 언제 delay 된 후에 실행시킬지 알려주어 set
            // Receiver에게 broadcast해주고 receiver는 이를 받아서 실행행

           alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
            // NotificationManager: 알림을 발생시키는 SystemService

            //-> 위에 두 코드는 아마 알림을 발생시키는 것을 하기 전에 초기화를 시키는 행위인 듯 하다.
        }



        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }



    enum class TimerState {
        Stopped, Paused, Running
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_main, container, false)

        val paperAdapter = PagerAdapter(childFragmentManager)
        view.view_pager.adapter = paperAdapter
        view.indicator.setViewPager(view.view_pager)

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()










//        view.tmp.setOnClickListener {
//            var sharedPreferences = requireActivity().getSharedPreferences("time",
//                AppCompatActivity.MODE_PRIVATE
//            )
//            var exp1 :Int? = sharedPreferences.getInt("TodayTime", 0)
//            var exp2 : String? = sharedPreferences.getString("TodayStrTime", "")
//            var exp2_split = exp2!!.split(',')
//
//
//            Log.d("AAAAAAAA", exp1.toString())
//            Log.d("AAAAAAAA", exp2!!)
//            Log.d("AAAAAAAA", exp2_split!!.toString())
//        }




        view.tmpTimeButton.setOnClickListener {
            val intent = Intent(requireContext(), TmpTimeActivity::class.java)
            startActivity(intent)
        }
        return view
    }








    override fun onPause() {
        super.onPause()


    }
}