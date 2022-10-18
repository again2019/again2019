package com.goingbacking.goingbacking.Service

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.goingbacking.goingbacking.LoginActivity
import com.goingbacking.goingbacking.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.IO).launch {
            delay(DURATION)
            var intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }
        
    }

    companion object {
        private const val DURATION : Long = 1000
    }

}

