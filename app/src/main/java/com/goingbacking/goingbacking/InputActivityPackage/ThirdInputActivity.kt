package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.children
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_second_input.*
import kotlinx.android.synthetic.main.activity_third_input.*

class ThirdInputActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var userInfoDTO : UserInfoDTO? = null
    var mutableList : MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_input)

        init()
        ThirdInputButton1.setOnClickListener {
            moveSecondInputPage()
        }

        ThirdInputButton2.setOnClickListener {

            firebaseFirestore?.collection("UserInfo")?.document(userId!!)?.update("whatToDo",mutableList.toString())
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



    private fun handleSelection() {
        chip_group.checkedChipIds.forEach {
            val chip = findViewById<Chip>(it)
            mutableList?.add("\n${chip.text}")
        }
    }

    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        userInfoDTO = UserInfoDTO()
        mutableList = mutableListOf<String>()

    }

    fun moveSecondInputPage() {
        val intent: Intent? = Intent(this, SecondInputActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        finish()

    }
}