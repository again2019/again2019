package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.Toast
import com.goingbacking.goingbacking.LoginActivity
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_first_input.*


class FirstInputActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var userInfoDTO : UserInfoDTO? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_input)

        init()

        firstInputButton.setOnClickListener {

            // 만약에 edittext가 비어있다면
            if (TextUtils.isEmpty(nickNameEdittext.text)) {
                Toast.makeText(this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else if (nickNameEdittext.text.length >= 10) {
                Toast.makeText(this, "닉네임이 너무 길어요.", Toast.LENGTH_SHORT).show()

            }
            // 만약에 edittext가 비어있지 않다면
            else {
                    moveSecondInputPage()
                }
            }

        //키보드 엔터 누르면 다음 페이지로 넘어갈 수 있는 코드
        nickNameEdittext.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KEYCODE_ENTER) {
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


    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        userInfoDTO = UserInfoDTO()
    }

    fun moveLoginPage() {
        val intent: Intent? = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        finish()
    }

    fun moveSecondInputPage() {
        //nickname 적는 edittext에서 정보 받아오기
        userInfoDTO = UserInfoDTO(nickNameEdittext.text.toString(), null, null, userId)
        firebaseFirestore?.collection("UserInfo")?.document(userId!!)?.set(userInfoDTO!!)

        val intent: Intent? = Intent(this, SecondInputActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)

        finish()
    }
}