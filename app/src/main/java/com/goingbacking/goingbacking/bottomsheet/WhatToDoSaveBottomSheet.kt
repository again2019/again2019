package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Main.First.FirstViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetWhatToDoSaveBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.makeGONE
import com.goingbacking.goingbacking.util.makeVisible
import com.goingbacking.goingbacking.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FieldValue
import io.grpc.okhttp.internal.framed.FrameReader

class WhatToDoSaveBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetWhatToDoSaveBinding
    private val viewModel: FirstViewModel by activityViewModels()
    private lateinit var simpleFormat1 : String //"yyyy-MM"
    private lateinit var simpleFormat2 : String //"dd"
    private lateinit var simpleFormat3 : String //"yyyy"
    private lateinit var simpleFormat4 : String //"MM"
    private lateinit var startTime : String

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
        startTime = arguments?.getString("startTime")!!

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
        viewModel.getWhatToDoInfo()
        viewModel.whatToDoListDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()

                    if (state.data.equals("")) {
                        binding.noTextView.makeVisible()
                        binding.chipGroup.makeGONE()
                    } else {
                        binding.noTextView.makeGONE()
                        binding.chipGroup.makeVisible()
                        val whatToDoList = state.data
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
                toast(requireContext(), "자기 계발을 한 개 선택해주세요.")
            } else {
                val count = FieldValue.increment(count_double)
                tmpTimeObserver(count)
                viewModel.updateWhatToDoMonthInfo(wakeUpTime1, selected.get(0), count)
                viewModel.updateWhatToDoYearInfo(wakeUpTime3, selected.get(0), count)
                viewModel.updateRankMonthInfo(wakeUpTime1, count)
                viewModel.updateRankYearInfo(wakeUpTime3, count)
                viewModel.deleteTmpTimeInfo(startTime)

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


    private fun tmpTimeObserver(count : FieldValue) {

        viewModel.updateTmpTimeDayInfo(wakeUpTime1, wakeUpTime2, count)
        viewModel.updateTmpTimeMonthInfo(wakeUpTime3, wakeUpTime4, count)
        viewModel.updateTmpTimeYearInfo(wakeUpTime3, count)
    }
}