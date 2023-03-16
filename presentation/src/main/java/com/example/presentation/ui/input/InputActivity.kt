package com.example.presentation.ui.input

import com.example.presentation.databinding.ActivityInputBinding
import com.example.presentation.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : BaseActivity<ActivityInputBinding>({
    ActivityInputBinding.inflate(it)
})