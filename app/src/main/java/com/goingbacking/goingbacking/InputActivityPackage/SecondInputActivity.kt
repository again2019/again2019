package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.View
import kotlinx.android.synthetic.main.activity_second_input.*

class SecondInputActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var userInfoDTO : UserInfoDTO? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_input)

        init()

        SecondInputButton1.setOnClickListener {
            moveFirstInputPage()
        }

        SecondInputButton2.setOnClickListener {
            firebaseFirestore?.collection("UserInfo")?.document(userId!!)?.update("userType", SecondInputSpinner.selectedItem.toString())
            moveThirdInputPage()
        }

        val arrayAdapter = ArrayAdapter.createFromResource(this, R.array.type,
        android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        SecondInputSpinner.setAdapter(arrayAdapter)

    }


    override fun onBackPressed() {
        super.onBackPressed()

        moveFirstInputPage()
    }


    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        userInfoDTO = UserInfoDTO()
    }

    fun moveFirstInputPage() {
        val intent: Intent? = Intent(this, FirstInputActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        finish()
    }

    fun moveThirdInputPage() {
        val intent: Intent? = Intent(this, ThirdInputActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        finish()

    }
}