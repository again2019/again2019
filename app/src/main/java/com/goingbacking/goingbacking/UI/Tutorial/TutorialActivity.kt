package com.goingbacking.goingbacking.UI.Tutorial

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import com.goingbacking.goingbacking.Adapter.TutorialViewPagerAdapter
import com.goingbacking.goingbacking.BR.CountReceiver
import com.goingbacking.goingbacking.BR.DeviceBootReceiver
import com.goingbacking.goingbacking.UI.Main.MainActivity
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.ViewModel.AlarmViewModel
import com.goingbacking.goingbacking.databinding.ActivityTutorialBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TutorialActivity : BaseActivity<ActivityTutorialBinding>({
    ActivityTutorialBinding.inflate(it)
}) {

    private lateinit var tutorialViewPagerAdapter : TutorialViewPagerAdapter
    private val viewModel : AlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()

        binding.TutorialButton.setOnClickListener {
            observer()
            notification()
            moveMainPage()
        }
    }

    private fun observer() {
        viewModel.addInitSaveTimeDayInfo()
        viewModel.addFirstInitSaveTimeMonthInfo()
        viewModel.addFirstInitSaveTimeYearInfo()
    }


    private fun notification() {
        //notification 구현 완료!! 합치기만 함녀돼
        //앞에 설정한 값으로 보여주기
        // 없으면 디폴트 값은 현재 시간
        var calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 10)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }

        var dailyNotify = true
        var pm: PackageManager = this.packageManager
        var receiver = ComponentName(this, DeviceBootReceiver::class.java)
        var alarmIntent = Intent(this, CountReceiver::class.java)
        alarmIntent.putExtra("id", 3000)
        alarmIntent.putExtra("type", "channel")

        var pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            this, 3000, alarmIntent, PendingIntent.FLAG_MUTABLE
        )

        var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (dailyNotify) {
            if (alarmManager != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                }
            }

            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )

        }
    }

    private fun moveMainPage() {
        var intent: Intent? = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initAdapter() {
        val fragmentList = listOf(Tutorial1Fragment(), Tutorial2Fragment())
        tutorialViewPagerAdapter = TutorialViewPagerAdapter(this)
        tutorialViewPagerAdapter.fragments.addAll(fragmentList)

        binding.TutorialViewPager.adapter = tutorialViewPagerAdapter

    }


}