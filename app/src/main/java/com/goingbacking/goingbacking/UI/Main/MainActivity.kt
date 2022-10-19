package com.goingbacking.goingbacking.UI.Main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>({
    ActivityMainBinding.inflate(it)
}) {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         initBottomNavigation()

    }

    private fun initBottomNavigation() {
        NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.nav_host))
    }
}


