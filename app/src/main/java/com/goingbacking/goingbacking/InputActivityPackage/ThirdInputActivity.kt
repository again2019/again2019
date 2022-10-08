package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.children
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.ActivityThirdInputBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdInputActivity : AppCompatActivity() {
    var mutableList : MutableList<String> = mutableListOf<String>()

    private val binding: ActivityThirdInputBinding by lazy {
        ActivityThirdInputBinding.inflate(layoutInflater)
    }
    val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ThirdInputObserver()

        binding.ThirdInputButton1.setOnClickListener {
            moveSecondInputPage()
        }

        binding.ThirdInputButton2.setOnClickListener {
            val selected = binding.chipGroup.children.toList()
                .filter{ (it as Chip).isChecked}.joinToString(",")
                {(it as Chip).text}

            viewModel.updateThirdInput(selected)
            moveTutorialPage()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveSecondInputPage()
    }

    private fun ThirdInputObserver() {
        viewModel.updateThirdInput.observe(this) {
                state ->
            when(state) {
                is UiState.Failure -> {
                    Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun moveSecondInputPage() {
        val intent: Intent? = Intent(this, SecondInputActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        finish()

    }

    fun moveTutorialPage() {
        val intent: Intent? = Intent(this, TutorialActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        finish()
    }
}