package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.children
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.ActivityThirdInputBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_second_input.*
import kotlinx.android.synthetic.main.activity_third_input.*

@AndroidEntryPoint
class ThirdInputActivity : AppCompatActivity() {
    var mutableList : MutableList<String> = mutableListOf<String>()

    private val binding: ActivityThirdInputBinding by lazy {
        ActivityThirdInputBinding.inflate(layoutInflater)
    }
    val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_input)

        ThirdInputObserver()

        binding.ThirdInputButton1.setOnClickListener {
            moveSecondInputPage()
        }

        binding.ThirdInputButton2.setOnClickListener {
            viewModel.updateThirdInput(mutableList.toString())
            val intent: Intent? = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
            finish()
        }

        //눌러진 것이 있는가?
        chip_group.children.forEach {
            (it as Chip).setOnCheckedChangeListener {
                buttonView, isChecked -> handleSelection()
            }
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

    private fun handleSelection() {
        chip_group.checkedChipIds.forEach {
            val chip = findViewById<Chip>(it)
            mutableList?.add("\n${chip.text}")
        }
    }


    fun moveSecondInputPage() {
        val intent: Intent? = Intent(this, SecondInputActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        finish()

    }
}