package com.goingbacking.goingbacking.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ui.login.LoginActivity

import com.goingbacking.goingbacking.util.Constants.Companion.DURATION
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val window = getWindow()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorMain)

        CoroutineScope(Dispatchers.IO).launch {
            delay(DURATION)
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()


        }
    }
}


