package com.goingbacking.goingbacking.UI

import android.content.Intent
import android.os.Bundle
import com.goingbacking.goingbacking.UI.Login.LoginActivity
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivitySplashBinding
import com.goingbacking.goingbacking.util.FBConstants.Companion.DURATION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>({
    ActivitySplashBinding.inflate(it)
}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            delay(DURATION)
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}

