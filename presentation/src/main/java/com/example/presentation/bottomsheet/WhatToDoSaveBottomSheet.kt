package com.example.presentation.bottomsheet

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.activityViewModels
import com.example.domain.util.UiState
import com.example.presentation.ui.main.first.FirstViewModel
import com.example.domain.util.makeGONE
import com.example.domain.util.makeVisible
import com.example.domain.util.toast
import com.example.presentation.R
import com.example.presentation.databinding.BottomSheetWhatToDoSaveBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WhatToDoSaveBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetWhatToDoSaveBinding
    private val viewModel: FirstViewModel by activityViewModels()
    private lateinit var simpleFormat1 : String //"yyyy-MM"
    private lateinit var simpleFormat2 : String //"dd"
    private lateinit var simpleFormat3 : String //"yyyy"
    private lateinit var simpleFormat4 : String //"MM"
    private lateinit var wakeUpTime : String

    private lateinit var wakeUpTime1 : String
    private lateinit var wakeUpTime2 : String
    private lateinit var wakeUpTime3 : String
    private lateinit var wakeUpTime4 : String

    private var count_double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetWhatToDoSaveBinding.inflate(inflater, container, false)

        count_double = arguments?.getDouble("count_double")!!
        simpleFormat1 = arguments?.getString("simpleFormat1")!!
        simpleFormat2 = arguments?.getString("simpleFormat2")!!
        simpleFormat3 = arguments?.getString("simpleFormat3")!!
        simpleFormat4 = arguments?.getString("simpleFormat4")!!
        wakeUpTime = arguments?.getString("wakeUpTime")!!

        wakeUpTime1 = arguments?.getString("wakeUpTime1")!!
        wakeUpTime2 = arguments?.getString("wakeUpTime2")!!
        wakeUpTime3 = arguments?.getString("wakeUpTime3")!!
        wakeUpTime4 = arguments?.getString("wakeUpTime4")!!

        binding.time.text = simpleFormat1
        binding.date.text = simpleFormat2
        binding.from.text = simpleFormat3
        binding.to.text = simpleFormat4

        whatToDoObserver()
        onClick()

        return binding.root
    }


    private fun whatToDoObserver() {
        viewModel.getFifthUserInfo()
        viewModel.userInfoDTO.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()

                    if (state.data.equals("")) {
                        binding.noTextView.makeVisible()
                        binding.chipGroup.makeGONE()
                    } else {
                        binding.noTextView.makeGONE()
                        binding.chipGroup.makeVisible()
                        val whatToDoList = state.data.whatToDoList
                        if (whatToDoList.size == 1) {
                            binding.chip1.text = whatToDoList.get(0)
                        } else if (whatToDoList.size == 2) {
                            binding.chip1.text = whatToDoList.get(0)
                            binding.chip2.text = whatToDoList.get(1)
                            binding.chip3.makeGONE()
                        } else if( whatToDoList.size == 3) {
                            binding.chip1.text = whatToDoList.get(0)
                            binding.chip2.text = whatToDoList.get(1)
                            binding.chip3.text = whatToDoList.get(2)
                        }
                    }
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Toast.makeText(requireContext(), R.string.whatToDo_update_time_fail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun onClick() = with(binding) {
        binding.whatToDoSaveButton.setOnClickListener {
            val selected = mutableListOf<String>()
            chipGroup.checkedChipIds.forEach {
                val chip = root.findViewById<Chip>(it).text.toString()
                selected.add(chip)
            }

            if (selected.size == 0) {
                toast(requireContext(), getString(R.string.whattodobottomsheet_ment1))
            } else {
                tmpTimeObserver(count_double)
                viewModel.updateWhatToDoMonthInfo(wakeUpTime1, selected.get(0), count_double)
                viewModel.updateWhatToDoYearInfo(wakeUpTime3, selected.get(0), count_double)
                viewModel.updateRankMonthInfo(wakeUpTime1, count_double)
                viewModel.updateRankYearInfo(wakeUpTime3, count_double)
                viewModel.deleteTmpTimeInfo(wakeUpTime)

                dialog!!.cancel()

            }
        }

        binding.xBtn.setOnClickListener {
            dialog!!.dismiss()

        }
        binding.exitButton.setOnClickListener {
            dialog!!.dismiss()
        }
    }


    private fun tmpTimeObserver(count : Double) {

        viewModel.updateTmpTimeDayInfo(wakeUpTime1, wakeUpTime2, count)
        viewModel.updateTmpTimeMonthInfo(wakeUpTime3, wakeUpTime4, count)
        viewModel.updateTmpTimeYearInfo(wakeUpTime3, count)
    }
}