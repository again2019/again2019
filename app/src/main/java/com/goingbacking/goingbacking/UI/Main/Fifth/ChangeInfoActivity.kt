package com.goingbacking.goingbacking.UI.Main.Fifth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.children
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.UI.Input.InputViewModel
import com.goingbacking.goingbacking.databinding.ActivityChangeInfoBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.toast
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>({
    ActivityChangeInfoBinding.inflate(it)
}) {
    var historyWhatToDo = mutableSetOf<String>()
    val viewModel: InputViewModel by viewModels()

    var now = LocalDate.now()
    var Strnow1 = now.format(DateTimeFormatter.ofPattern("MM")).toInt()
    var Strnow2 = now.format(DateTimeFormatter.ofPattern("yyyy")).toInt()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        historyWhatToDo = PrefUtil.getHistoryWhatToDo(this)!!

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

        }


    }
    private fun onClick() = with(binding) {


        infoChangeButton.setOnClickListener {


            val selected = chipGroup.children.toList()
                .filter{ (it as Chip).isChecked }.joinToString(",")
                {(it as Chip).text}

            if (selected.equals(""))  {
                toast(this@ChangeInfoActivity, getString(R.string.chip_no_selected))
            }  else {
                for (whattodo in selected.split(',')) {
                    if (!historyWhatToDo.contains(whattodo)) {
                        val whatToDoMonthDTO = WhatToDoMonthDTO()
                        whatToDoMonthDTO.count = 0
                        whatToDoMonthDTO.month = Strnow1
                        whatToDoMonthDTO.whatToDo = whattodo
                        viewModel.addInitWhatToDoMonthTime(whatToDoMonthDTO)

                        val whatToDoYearDTO = WhatToDoYearDTO()
                        whatToDoYearDTO.count = 0
                        whatToDoYearDTO.year = Strnow2
                        whatToDoYearDTO.whatToDo = whattodo
                        viewModel.addInitWhatToDoYearTime(whatToDoYearDTO)

                    }
                    historyWhatToDo.add(whattodo)
                }

                PrefUtil.setHistoryWhatToDo(historyWhatToDo, this@ChangeInfoActivity)

                val userInfoDTO = UserInfoDTO()
                userInfoDTO.userNickName = changeNickNameEditText.text.toString()
                userInfoDTO.userType = changeTypeEditText.text.toString()
                userInfoDTO.whatToDo = selected
//                viewModel.addFirstInput(userInfoDTO)
                finish()
            }





        }



    }

}