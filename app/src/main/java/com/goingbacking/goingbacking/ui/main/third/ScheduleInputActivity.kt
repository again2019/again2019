package com.goingbacking.goingbacking.ui.main.third


import com.goingbacking.goingbacking.ui.base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityScheduleInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleInputActivity : BaseActivity<ActivityScheduleInputBinding>({
    ActivityScheduleInputBinding.inflate(it)
})
