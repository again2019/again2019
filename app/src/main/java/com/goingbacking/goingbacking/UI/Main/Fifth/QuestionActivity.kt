package com.goingbacking.goingbacking.UI.Main.Fifth

import android.content.Intent
import android.net.Uri
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
        layout01.setOnClickListener{
            if(layoutDetail01.visibility == View.VISIBLE) {
                layoutDetail01.makeGONE()
                layoutBtn01.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                layoutDetail01.makeVisible()
                layoutBtn01.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }
        layout02.setOnClickListener{
            if(layoutDetail02.visibility == View.VISIBLE) {
                layoutDetail02.makeGONE()
                layoutBtn02.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                layoutDetail02.makeVisible()
                layoutBtn02.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

        layout03.setOnClickListener{
            if(layoutDetail03.visibility == View.VISIBLE) {
                layoutDetail03.makeGONE()
                layoutBtn02.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                layoutDetail03.makeVisible()
                layoutBtn03.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

        layout04.setOnClickListener{
            if(layoutDetail04.visibility == View.VISIBLE) {
                layoutDetail04.makeGONE()
                layoutBtn04.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                layoutDetail04.makeVisible()
                layoutBtn04.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

        layout05.setOnClickListener{
            if(layoutDetail05.visibility == View.VISIBLE) {
                layoutDetail05.makeGONE()
                layoutBtn05.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                layoutDetail05.makeVisible()
                layoutBtn05.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }
        linkButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSf_rvMCDbYi_poib9CAzN-wulhDbFB0tMd0g8BgxLsRpdLAog/viewform?usp=sf_link")
            intent.setData(uri)
            startActivity(intent)
        }

    }



}