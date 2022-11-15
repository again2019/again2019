package com.goingbacking.goingbacking.UI.Main.Third


import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityScheduleInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleInputActivity : BaseActivity<ActivityScheduleInputBinding>({
    ActivityScheduleInputBinding.inflate(it)
})
