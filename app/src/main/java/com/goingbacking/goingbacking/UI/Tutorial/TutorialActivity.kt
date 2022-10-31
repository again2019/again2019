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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()

        binding.TutorialButton.setOnClickListener {
            observer()
            moveMainPage()
        }
    }

    private fun observer() {
        viewModel.addInitSaveTimeDayInfo()
        viewModel.addFirstInitSaveTimeMonthInfo()
        viewModel.addFirstInitSaveTimeYearInfo()
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