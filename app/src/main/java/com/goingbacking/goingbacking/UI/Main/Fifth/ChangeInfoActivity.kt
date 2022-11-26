package com.goingbacking.goingbacking.UI.Main.Fifth

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.databinding.ActivityChangeInfoBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.currentday
import com.goingbacking.goingbacking.util.toast
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChangeInfoActivity : BaseActivity<ActivityChangeInfoBinding>({
    ActivityChangeInfoBinding.inflate(it)
}) {
    var historyWhatToDo = mutableSetOf<String>()
    val viewModel: FifthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        historyWhatToDo = PrefUtil.getHistoryWhatToDo(this)!!
        toast(this, historyWhatToDo.toString())
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backbtn.setOnClickListener {
            finish()
        }

        editInit()
        onClick()
    }

    private fun editInit() = with(binding) {
        changeNickNameEditText.setText(intent.getStringExtra("nickName"))
        changeTypeEditText.setText(intent.getStringExtra("userType"))

        val whatToDo = intent.getStringArrayExtra("whatToDo")!!.toList()


        for (i in whatToDo) {
            if (i.equals("독서")) { chip1.isChecked = true}
            if (i.equals("영어 듣기")) { chip2.isChecked = true }
            if (i.equals("시사 공부")) { chip3.isChecked = true }
            if (i.equals("전공 과제")) { chip4.isChecked = true }
            if (i.equals("뉴스")) { chip5.isChecked = true}

        }


    }
    private fun onClick() = with(binding) {


        infoChangeButton.setOnClickListener {


            val selected = mutableListOf<String>()
            chipGroup.checkedChipIds.forEach {
                val chip = root.findViewById<Chip>(it).text.toString()
                selected.add(chip)
            }

            if (selected.size == 0)  {
                toast(this@ChangeInfoActivity, getString(R.string.chip_no_selected))
            }  else {
                if (selected.size <= 3) {
                    for (whattodo in selected) {
                        if (!historyWhatToDo.contains(whattodo)) {
                            val whatToDoMonthDTO = WhatToDoMonthDTO(
                                count = 0,
                                month = currentday("MM").toInt(),
                                whatToDo = whattodo
                            )

                            viewModel.addInitWhatToDoMonthTime(whatToDoMonthDTO)

                            val whatToDoYearDTO = WhatToDoYearDTO(
                                count = 0,
                                year = currentday("yyyy").toInt(),
                                whatToDo = whattodo
                            )

                            viewModel.addInitWhatToDoYearTime(whatToDoYearDTO)

                        }
                        historyWhatToDo.add(whattodo)
                    }
                    PrefUtil.setHistoryWhatToDo(historyWhatToDo, this@ChangeInfoActivity)

                    viewModel.reviseUserInfo(changeNickNameEditText.text.toString(), changeTypeEditText.text.toString(), selected.toList())


                    finish()
                }
                else {
                    toast(this@ChangeInfoActivity, "3개 이하로 선택해주세요.")
                }


            }
        }
    }

}