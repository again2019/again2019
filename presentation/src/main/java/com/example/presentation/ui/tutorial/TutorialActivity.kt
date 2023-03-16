package com.example.presentation.ui.tutorial

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel
import com.example.presentation.br.CountReceiver
import com.example.presentation.ui.main.MainActivity
import com.example.presentation.ui.base.BaseActivity
import com.example.domain.util.Constants
import com.example.domain.util.calendar
import com.example.domain.util.currentday
import com.example.presentation.adapter.TutorialViewPagerAdapter
import com.example.presentation.databinding.ActivityTutorialBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TutorialActivity : BaseActivity<ActivityTutorialBinding>({
    ActivityTutorialBinding.inflate(it)
}) {
    val viewModel: TutorialViewModel by viewModels()

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
        val savedTimeDayModel  = SavedTimeDayModel(
            day = currentday("dd").toInt() ,
            month = currentday("MM").toInt(),
            year = currentday("yyyy").toInt(),
            count = 0
        )

        val savedTimeMonthModel = SavedTimeMonthModel(
            month = currentday("MM").toInt(),
            year = currentday("yyyy").toInt(),
            count = 0
        )

        val savedTimeYearModel = SavedTimeYearModel(
            year = currentday("yyyy").toInt(),
            count = 0
        )

        viewModel.addMySavedTimeDay(savedTimeDayModel)
        viewModel.addMySavedTimeMonth(savedTimeMonthModel)
        viewModel.addMySavedTimeYear(savedTimeYearModel)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun initAdapter() {
        val fragmentList = listOf(Tutorial1Fragment(), Tutorial2Fragment(), Tutorial3Fragment())
        tutorialViewPagerAdapter = TutorialViewPagerAdapter(this)
        tutorialViewPagerAdapter.fragments.addAll(fragmentList)

        binding.TutorialViewPager.adapter = tutorialViewPagerAdapter
        binding.dotsIndicator.setViewPager2(binding.TutorialViewPager)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun notification() {
        val calendar = calendar(0,0,0,0)
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