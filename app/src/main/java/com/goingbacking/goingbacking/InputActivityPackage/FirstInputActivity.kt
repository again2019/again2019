package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.goingbacking.goingbacking.LoginActivity
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.ActivityFirstInputBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_first_input.*

@AndroidEntryPoint
class FirstInputActivity : AppCompatActivity() {
    private val binding: ActivityFirstInputBinding by lazy {
        ActivityFirstInputBinding.inflate(layoutInflater)
    }
    val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.addFirstInput.observe(this) {
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

        binding.firstInputButton.setOnClickListener {
            // 만약에 edittext가 비어있다면
            if (TextUtils.isEmpty(nickNameEdittext.text)) {
                Toast.makeText(this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if (nickNameEdittext.text.length >= 10) {
                Toast.makeText(this, "닉네임이 너무 길어요.", Toast.LENGTH_SHORT).show()
            }
            // 만약에 edittext가 비어있지 않다면
            else {
                    viewModel.addFirstInput(
                        UserInfoDTO(
                            nickNameEdittext.text.toString(),
                            null,
                            null,
                            null,
                        )
                    )
                }
            }

        //키보드 엔터 누르면 다음 페이지로 넘어갈 수 있는 코드
        binding.nickNameEdittext.setOnKeyListener { v, keyCode, event ->
            if ( keyCode == KEYCODE_ENTER) {
                if (TextUtils.isEmpty(nickNameEdittext.text)) {
                    Toast.makeText(this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
                else if (nickNameEdittext.text.length >= 10) {
                    Toast.makeText(this, "닉네임이 너무 길어요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    moveSecondInputPage()
                    }
                }
                true
            }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveLoginPage()
    }

    fun moveLoginPage() {
        val intent: Intent? = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        finish()
    }

    fun moveSecondInputPage() {

        val intent: Intent? = Intent(this, SecondInputActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)

        finish()
    }
}