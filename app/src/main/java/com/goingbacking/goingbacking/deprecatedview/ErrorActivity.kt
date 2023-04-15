package com.goingbacking.goingbacking.deprecatedview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.base.ActivityLauncher
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass


@AndroidEntryPoint
class ErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
    }


    companion object : ActivityLauncher<ErrorActivity>() {
        const val EXTRA_INTENT = "EXTRA_INTENT"
        const val EXTRA_ERROR_TEXT = "EXTRA_ERROR_TEXT"

        override val activityClass: KClass<ErrorActivity>
            get() = ErrorActivity::class

    }
}