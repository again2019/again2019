package com.example.presentation.ui.main.fifth

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel
import com.example.presentation.ui.base.BaseActivity

import com.example.domain.util.currentday
import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.PrefUtil
import com.example.presentation.R
import com.example.presentation.databinding.ActivityChangeInfoBinding
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
            if (i.equals(getString(R.string.self_dev1))) { chip1.isChecked = true}
            if (i.equals(getString(R.string.self_dev2))) { chip2.isChecked = true}
            if (i.equals(getString(R.string.self_dev3))) { chip3.isChecked = true}
            if (i.equals(getString(R.string.self_dev4))) { chip4.isChecked = true}
            if (i.equals(getString(R.string.self_dev5))) { chip5.isChecked = true}
            if (i.equals(getString(R.string.self_dev6))) { chip6.isChecked = true}
            if (i.equals(getString(R.string.self_dev7))) { chip7.isChecked = true}
            if (i.equals(getString(R.string.self_dev8))) { chip8.isChecked = true}
            if (i.equals(getString(R.string.self_dev9))) { chip9.isChecked = true}
            if (i.equals(getString(R.string.self_dev10))) { chip10.isChecked = true}
            if (i.equals(getString(R.string.self_dev11))) { chip11.isChecked = true}
            if (i.equals(getString(R.string.self_dev12))) { chip12.isChecked = true}
        }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClick() = with(binding) {
        infoChangeButton.setOnClickListener {
            if (!NetworkManager.checkNetworkState(this@ChangeInfoActivity)) {
                toast(this@ChangeInfoActivity, getString(R.string.network_fail))
            } else {
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
                                val whatToDoMonthModel = WhatToDoMonthModel(
                                    count = 0,
                                    month = currentday("MM").toInt(),
                                    whatToDo = whattodo
                                )

                                viewModel.addInitWhatToDoMonthTime(whatToDoMonthModel)

                                val whatToDoYearModel = WhatToDoYearModel(
                                    count = 0,
                                    year = currentday("yyyy").toInt(),
                                    whatToDo = whattodo
                                )
                                viewModel.addInitWhatToDoYearTime(whatToDoYearModel)
                            }
                            historyWhatToDo.add(whattodo)
                        }
                        PrefUtil.setHistoryWhatToDo(historyWhatToDo, this@ChangeInfoActivity)
                        viewModel.updateFifthUserInfo(changeNickNameEditText.text.toString(), changeTypeEditText.text.toString(), selected.toList())
                        finish()
                    }
                    else {
                        toast(this@ChangeInfoActivity, "3개 이하로 선택해주세요.")
                    }
                }
            }
        }
    }

}