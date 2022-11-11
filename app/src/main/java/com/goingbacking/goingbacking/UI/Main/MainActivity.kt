package com.goingbacking.goingbacking.UI.Main


import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.goingbacking.goingbacking.BR.CountReceiver
import com.goingbacking.goingbacking.BR.DeviceBootReceiver
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityMainBinding
import com.goingbacking.goingbacking.util.Constants.Companion.CHANNEL
import com.goingbacking.goingbacking.util.Constants.Companion.ID
import com.goingbacking.goingbacking.util.Constants.Companion.TYPE
import com.goingbacking.goingbacking.util.Constants.Companion.VALUE
import com.goingbacking.goingbacking.util.calendar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}) {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBottomNavigation()

    }

    private fun initBottomNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host))
    }


}


