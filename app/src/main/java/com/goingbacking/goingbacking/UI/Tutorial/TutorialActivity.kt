package com.goingbacking.goingbacking.UI.Tutorial

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
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

    companion object {
        private const val ID = "id"
        private const val TYPE = "type"
        private const val CHANNEL = "channel"
        private const val VALUE = 3000
    }
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
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 5)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }

        val pm: PackageManager = this.packageManager
        val receiver = ComponentName(this, DeviceBootReceiver::class.java)
        val alarmIntent = Intent(this, CountReceiver::class.java)
        alarmIntent.putExtra(ID, VALUE)
        alarmIntent.putExtra(TYPE, CHANNEL)

        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            this, VALUE, alarmIntent, FLAG_IMMUTABLE
        )

        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
            )

            pm.setComponentEnabledSetting(
                receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        }

    private fun moveMainPage() {
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


}