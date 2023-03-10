package com.goingbacking.goingbacking.ui.main.fifth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.goingbacking.goingbacking.ui.base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityQuestionBinding
import com.goingbacking.goingbacking.util.makeGONE
import com.goingbacking.goingbacking.util.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import android.view.View

@AndroidEntryPoint
class QuestionActivity : BaseActivity<ActivityQuestionBinding>({
    ActivityQuestionBinding.inflate(it)
}) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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