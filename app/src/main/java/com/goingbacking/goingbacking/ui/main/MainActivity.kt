package com.goingbacking.goingbacking.ui.main



import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI

import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ui.base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityMainBinding

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


