package com.goingbacking.goingbacking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        Handler().postDelayed({
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, DURATION)

    }

    companion object {
        private const val DURATION : Long = 1000
    }

}

