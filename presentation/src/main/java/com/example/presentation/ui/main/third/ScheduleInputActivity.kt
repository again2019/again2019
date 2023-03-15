package com.example.presentation.ui.main.third


import com.example.presentation.databinding.ActivityScheduleInputBinding
import com.example.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScheduleInputActivity : BaseActivity<ActivityScheduleInputBinding>({
    ActivityScheduleInputBinding.inflate(it)
})
