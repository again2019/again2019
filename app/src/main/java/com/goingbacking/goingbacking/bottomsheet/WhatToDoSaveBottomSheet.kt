package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FieldValue

class WhatToDoSaveBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetWhatToDoSaveBinding
    private val viewModel: FirstViewModel by activityViewModels()
    private lateinit var simpleFormat1 : String
    private lateinit var simpleFormat2 : String
    private lateinit var simpleFormat3 : String
    private lateinit var simpleFormat4 : String
    private var count_double = 0.0
    private var finalWhatToDo = ""

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

        binding.time.text = simpleFormat1
        binding.date.text = simpleFormat2
        binding.from.text = simpleFormat3
        binding.to.text = simpleFormat4

        whatToDoObserver()

        onClick()

        return binding.root
    }

    private fun onClick() = with(binding) {
        binding.whatToDoSaveButton.setOnClickListener {
            monthObserver(finalWhatToDo, FieldValue.increment(count_double))
            yearObserver(finalWhatToDo, FieldValue.increment(count_double))
            dismiss()
        }
        binding.xBtn.setOnClickListener {
            dismiss()
        }
        binding.exitButton.setOnClickListener {
            dismiss()
        }
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


    private fun monthObserver(whatToDoText :String, count: FieldValue) {
        viewModel.updateWhatToDoMonthInfo(whatToDoText, count)
        viewModel.whatToDoMonthDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
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

    private fun yearObserver(whatToDoText :String, count: FieldValue) {
        viewModel.updateWhatToDoYearInfo(whatToDoText, count)
        viewModel.whatToDoYearDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
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

}