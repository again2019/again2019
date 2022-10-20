package com.goingbacking.goingbacking.UI.Main.Fifth

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.ActivityChangeInfoBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>({
    ActivityChangeInfoBinding.inflate(it)
}) {

    val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editInit()
        onClick()
    }

    private fun editInit() = with(binding) {
        changeNickNameEditText.setText(intent.getStringExtra("nickName"))
        changeTypeEditText.setText(intent.getStringExtra("userType"))


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
    private fun onClick() = with(binding) {
        chipChangeAddButton.setOnClickListener {
            changeChipGroup.addView(Chip(this@ChangeInfoActivity).apply {
                text = chipChangeEditText.text.toString()
                isCloseIconVisible = true
                isCheckable = true
                isChecked = true
                setOnClickListener { isChecked = true }
                setOnCloseIconClickListener { changeChipGroup.removeView(this) }
                chipChangeEditText.setText("")
            })
        }

        infoChangeButton.setOnClickListener {
            val selected1 = chipGroup.children.toList()
                .filter{ (it as Chip).isChecked}.joinToString(",")
                {(it as Chip).text}
            var selected2 = changeChipGroup.children.toList()
                .filter{ (it as Chip).isChecked}.joinToString(",")
                {(it as Chip).text}
            var selected = selected1 + ',' + selected2

            var userInfoDTO = UserInfoDTO()
            userInfoDTO.userNickName = changeNickNameEditText.text.toString()
            userInfoDTO.userType = changeTypeEditText.text.toString()
            userInfoDTO.whatToDo = selected
            viewModel.addFirstInput(userInfoDTO)
            finish()

        }



    }

}