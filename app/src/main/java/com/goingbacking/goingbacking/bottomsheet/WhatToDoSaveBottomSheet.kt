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
import com.goingbacking.goingbacking.ViewModel.TmpTimeViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetWhatToDoSaveBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FieldValue

class WhatToDoSaveBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetWhatToDoSaveBinding
    private val viewModel: TmpTimeViewModel by activityViewModels()
    private var count_double = 0.0
    private var finalWhatToDo = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetWhatToDoSaveBinding.inflate(inflater, container, false)

        count_double = arguments?.getDouble("count")!!
        binding.whatToDoCount.text = count_double.toInt().toString()

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
                        binding.noTextView.isVisible = true
                        binding.chipGroup.isInvisible = true
                    } else {
                        binding.noTextView.isInvisible = true
                        binding.chipGroup.isVisible = true
                        val whatToDoList = state.data.split(",")
                        for (i in whatToDoList){
                            binding.chipGroup.addView(Chip(requireContext()).apply {
                                text = i
                                isCheckable = true
                                setOnClickListener{ finalWhatToDo = i }
                            })
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