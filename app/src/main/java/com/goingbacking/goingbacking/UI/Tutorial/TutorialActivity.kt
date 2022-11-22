package com.goingbacking.goingbacking.UI.Tutorial

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.goingbacking.goingbacking.Adapter.TutorialViewPagerAdapter
import com.goingbacking.goingbacking.BR.CountReceiver
import com.goingbacking.goingbacking.BR.DeviceBootReceiver
import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepository
import com.goingbacking.goingbacking.UI.Main.MainActivity
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityTutorialBinding
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.calendar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialActivity : BaseActivity<ActivityTutorialBinding>({
    ActivityTutorialBinding.inflate(it)
}) {
    private val alarmRepository = AlarmRepository()
    private lateinit var tutorialViewPagerAdapter : TutorialViewPagerAdapter
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        notification()

        binding.TutorialButton.setOnClickListener {
            moveMainPage()
        }
    }

    private fun moveMainPage() {
        alarmRepository.addInitSaveTimeDayInfo()
        alarmRepository.addFirstInitSaveTimeMonthInfo()
        alarmRepository.addFirstInitSaveTimeYearInfo()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun initAdapter() {
        val fragmentList = listOf(Tutorial1Fragment(), Tutorial2Fragment())
        tutorialViewPagerAdapter = TutorialViewPagerAdapter(this)
        tutorialViewPagerAdapter.fragments.addAll(fragmentList)

        binding.TutorialViewPager.adapter = tutorialViewPagerAdapter

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun notification() {
        val calendar = calendar(17,9,0,0)
        // 매일 12시마다 초기화가 되면 CountReceiver의 작업을 수행함.
        val alarmIntent = Intent(this, CountReceiver::class.java)
        alarmIntent.putExtra(Constants.ID, Constants.VALUE)
        alarmIntent.putExtra(Constants.TYPE, Constants.CHANNEL)

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            this, Constants.VALUE, alarmIntent, PendingIntent.FLAG_MUTABLE
        )

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

    }


}