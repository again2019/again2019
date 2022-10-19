package com.goingbacking.goingbacking.UI.Input

import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : BaseActivity<ActivityInputBinding>({
    ActivityInputBinding.inflate(it)
})