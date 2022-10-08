package com.goingbacking.goingbacking.InputActivityPackage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.ActivitySecondInputBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.View
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_second_input.*

@AndroidEntryPoint
class SecondInputActivity : AppCompatActivity() {

    private val binding: ActivitySecondInputBinding by lazy {
        ActivitySecondInputBinding.inflate(layoutInflater)
    }
    val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.SecondInputButton1.setOnClickListener {
            moveFirstInputPage()
        }

        binding.SecondInputButton2.setOnClickListener {
            viewModel.updateSecondInput(SecondInputSpinner.selectedItem.toString())
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