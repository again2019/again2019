package com.goingbacking.goingbacking.UI.Main.Fifth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityQuestionBinding
import com.goingbacking.goingbacking.util.makeGONE
import com.goingbacking.goingbacking.util.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle

@AndroidEntryPoint
class QuestionActivity : BaseActivity<ActivityQuestionBinding>({
    ActivityQuestionBinding.inflate(it)
}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        finish()
                        return true
                    }
                }
                return true
            }
        }, this, Lifecycle.State.RESUMED)
        
        onClick()
    }

    private fun onClick() = with(binding) {
        binding.layout01.setOnClickListener{
            if(binding.layoutDetail01.visibility == View.VISIBLE) {
                binding.layoutDetail01.makeGONE()
                binding.layoutBtn01.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                binding.layoutDetail01.makeVisible()
                binding.layoutBtn01.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }
    }



}