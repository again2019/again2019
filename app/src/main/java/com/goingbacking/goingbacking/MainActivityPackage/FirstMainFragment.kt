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
        private lateinit var timer: CountDownTimer // 순서대로 얼마나 타이머를 진행할지 나타냄, thread 사용할 필요 없음
        private var timerLengthSeconds = 0L
         var timerState = TimerState.Stopped
        private var secondsRemaining = 0L

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

        fun startTimer() {
            timerState = TimerState.Running // timer 상태를 running으로 바꾼다
            timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
                override fun onFinish() = onTimerFinished()
                override fun onTick(millisUntilFinished: Long) { // millisUntilFinished: 남아있는 시간, 수행간격 마다 호출
                    secondsRemaining = millisUntilFinished / 1000 // 남아있는 시간  =
                    updateCountdownUI() // UI에 표시됨
                }

            }.start()
        }

        fun onTimerFinished() {
            timerState = TimerState.Stopped
            // timerState =  Stop으로 둔다
            setNewTimerLength()
            // 다시 1초로 초기화한다.
            PrefUtil.setSecondsRemaining(timerLengthSeconds, requireActivity())
            secondsRemaining = timerLengthSeconds // 남아있는 시간 = 1분(60초)

            updateCountdownUI()
        }

        fun setNewTimerLength() {
            val lengthInMinutes = 60 //exp1
            // 1을 return 한다.
            Log.d("AAAAAAAA", " -> lengthInMinutes" + lengthInMinutes.toString())
            timerLengthSeconds = (lengthInMinutes!! * 60L)
            // timeLengthSeconds = 60이 됨
            Log.d("AAAAAAAA"," -> lengthInMinutes" +  timerLengthSeconds.toString())

        }

        fun setPreviousTimerLength() {
            timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(requireActivity())
            Log.d("setPreviousTimerLength -> TTTT", timerLengthSeconds.toString())
        }

        fun updateCountdownUI() { //실제로 표기되는 부분을 보여주는 것
            val minutesUntilFinished = secondsRemaining/60
            val secondsInMinuteUtilFinished = secondsRemaining - minutesUntilFinished * 60
            val secondsStr = secondsInMinuteUtilFinished.toString()
            textView_countdown.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }



    enum class TimerState {
        Stopped, Paused, Running
    }

    private lateinit var timer: CountDownTimer // 순서대로 얼마나 타이머를 진행할지 나타냄, thread 사용할 필요 없음
    private var timerLengthSeconds = 0L
    private var timerState = TimerState.Stopped
    private var secondsRemaining = 0L

    var sharedPreferences :SharedPreferences? = null
    var exp1 :Int? = null
    var exp2 : String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first_main, container, false)

        val paperAdapter = PagerAdapter(childFragmentManager)
        view.view_pager.adapter = paperAdapter
        view.indicator.setViewPager(view.view_pager)

        sharedPreferences = requireActivity().getSharedPreferences("time", AppCompatActivity.MODE_PRIVATE)
        exp1 = sharedPreferences!!.getInt("TodayTime", 0)
        exp2 = sharedPreferences!!.getString("TodayStrTime", "")

        var exp2_split = exp2!!.split(',').toMutableList()

        exp2_split.removeAt(0)

        var exp3 = mutableListOf<Any>()
        for(i in exp2_split) {
            Log.d("AAAAAAAA", i)
            var xx = i.split('-').toMutableList()
            exp3.add(xx)
        }

        Log.d("AAAAAAAA", exp1.toString())
        Log.d("AAAAAAAA", exp2!!)
        Log.d("AAAAAAAA", exp2_split!!.toString())
        Log.d("AAAAAAAA", exp3[0]!!.toString())

        var a = exp3[0] as List<Int>

        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()







        view.num.text = (exp1!! / 60).toString()



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





        view.tmptimerButton1.setOnClickListener{
            startTimer() // 계속해서 카운트다운을 하고 UI로 보여줌
            timerState = TimerState.Running
            updateButtons()


        }
        view.tmptimerButton2.setOnClickListener{
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()

        }
        view.tmptimerButton3.setOnClickListener{
            timer.cancel()
            onTimerFinished() // 1:00으로 다시 초기화함
        }

        view.tmpTimeButton.setOnClickListener {
            val intent = Intent(requireContext(), TmpTimeActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onResume() {
        super.onResume()

        initTimer()

        removeAlarm(requireActivity()) //알림을 없애주는 기능
        NotificationUtil.hideTimerNotification(requireActivity())
        //notification을 없애는 기능
    }

    private fun initTimer() {
        timerState = PrefUtil.getTimerState(requireActivity())
        // 처음 시작할 경우 timeState는 멈춰져 있음

        Log.d("AAAAAAAA", "initTimer -> timeState " + timerState.toString())

        if(timerState == TimerState.Stopped) setNewTimerLength() //만약에 아무것도 안하고 있을 경우 setNewTimerLength 호출
        else setPreviousTimerLength() //

        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(requireActivity())
        else timerLengthSeconds
        // 만약에 stopped 되어있을 경우 secondsRemaining = 60 이됨

        val alarmSetTime = PrefUtil.getAlarmSetTime(requireContext()) // alarmSetTime = 0으로 초기화됨
        Log.d("TTTT", "initTimer -> alarmSetTime " + alarmSetTime.toString())

        if (alarmSetTime > 0) secondsRemaining -= nowSeconds - alarmSetTime
        Log.d("TTTT", "initTimer -> secondsRemaining " + alarmSetTime.toString())


        if (secondsRemaining <= 0) onTimerFinished()
        else if (timerState == TimerState.Running) startTimer()

        updateButtons()
        updateCountdownUI()

    }

    fun onTimerFinished() {
        timerState = TimerState.Stopped
        // timerState =  Stop으로 둔다
        setNewTimerLength()
        // 다시 1초로 초기화한다.
        PrefUtil.setSecondsRemaining(timerLengthSeconds, requireActivity())
        secondsRemaining = timerLengthSeconds // 남아있는 시간 = 1분(60초)

        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer() {
        timerState = TimerState.Running // timer 상태를 running으로 바꾼다
        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish()  = onTimerFinished()
            override fun onTick(millisUntilFinished: Long) { // millisUntilFinished: 남아있는 시간, 수행간격 마다 호출
                secondsRemaining = millisUntilFinished / 1000 // 남아있는 시간  =
                updateCountdownUI() // UI에 표시됨
            }

        }.start()
    }



    private fun updateButtons(){ // 버튼의 상태를 바꾸기
        when (timerState) {
            TimerState.Running ->{
                tmptimerButton1.isEnabled = false
                tmptimerButton2.isEnabled = true
                tmptimerButton3.isEnabled = true
            }
            TimerState.Stopped -> {
                tmptimerButton1.isEnabled = true
                tmptimerButton2.isEnabled = false
                tmptimerButton3.isEnabled = false
            }
            TimerState.Paused -> {
                tmptimerButton1.isEnabled = true
                tmptimerButton2.isEnabled = false
                tmptimerButton3.isEnabled = true
            }
        }
    }


    override fun onPause() {
        super.onPause()

        if (timerState == TimerState.Running) {
            timer.cancel() // CountDown을 취소해야지 오류가 나지 않음 -> background에서 다시 돌아왔을 때 오류가 날 수 있음
            val wakeUpTime = setAlarm(requireContext(), nowSeconds, secondsRemaining) // 이걸 해야지 백그라운드 상태에서 시간이 흘러감.
            // Alarm을 만들어냄 nowSeconds =  지금 시간, secondsRemaining = 남아있는 시간
            NotificationUtil.showTimerRunning(requireActivity(), wakeUpTime)
            // notification을 만들어냄
        // start background timer and show notification
        } else if (timerState == TimerState.Paused) {
            NotificationUtil.showTimerPaused(requireActivity())


            // show notification
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, requireActivity())
        PrefUtil.setSecondsRemaining(secondsRemaining, requireActivity())
        PrefUtil.setTimerState(timerState, requireActivity())

    }
}