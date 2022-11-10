package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : BaseActivity<ActivityInputBinding>({
    ActivityInputBinding.inflate(it)
})