package com.goingbacking.goingbacking.UI.Main.Fifth

import android.os.Bundle
import android.util.Log
import androidx.core.view.children
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityChangeInfoBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>({
    ActivityChangeInfoBinding.inflate(it)
}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editInit()



    }



    private fun editInit() = with(binding) {
        changeNickNameEditText.setText(intent.getStringExtra("nickName"))
        changeTypeEditText.setText(intent.getStringExtra("userType"))
        infoChangeButton.setOnClickListener { finish() }

        val whatToDo = intent.getStringExtra("whatToDo")!!.split(",")

        for (i in whatToDo) {
            if (i.equals("독서")) { chip1.isChecked }
            else if (i.equals("영어 듣기")) { chip2.isChecked = true }
            else if (i.equals("시사 공부")) { chip3.isChecked = true }
            else if (i.equals("전공 과제")) { chip4.isChecked = true }
            else if (i.equals("뉴스")) { chip5.isChecked = true}
            else {
                changeChipGroup.addView(Chip(this@ChangeInfoActivity).apply{
                    text = i
                    isCloseIconVisible = true
                    isCheckable = true
                    isChecked = true
                    setOnClickListener { isChecked = true }
                    setOnCloseIconClickListener { changeChipGroup.removeView(this) }
                })
            }
        }


    }
}