package com.goingbacking.goingbacking.ui.input

import com.goingbacking.goingbacking.ui.base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : BaseActivity<ActivityInputBinding>({
    ActivityInputBinding.inflate(it)
})