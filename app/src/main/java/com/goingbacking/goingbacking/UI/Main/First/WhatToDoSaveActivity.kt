package com.goingbacking.goingbacking.UI.Main.First

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.children
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.ViewModel.TmpTimeViewModel
import com.goingbacking.goingbacking.databinding.ActivityWhatToDoSaveBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhatToDoSaveActivity : BaseActivity<ActivityWhatToDoSaveBinding>({
    ActivityWhatToDoSaveBinding.inflate(it)
}) {
    val viewModel :TmpTimeViewModel by viewModels()
    var finalWhatToDo = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val count_double = intent.getDoubleExtra("count", 0.0)
        Log.d("experiment", count_double.toString())

        whatToDoObserver()




        binding.whatToDoSaveButton.setOnClickListener {



            Log.d("experiment", finalWhatToDo)
            monthObserver(finalWhatToDo, FieldValue.increment(count_double))
            yearObserver(finalWhatToDo, FieldValue.increment(count_double))
        }

    }

    private fun whatToDoObserver() {
        viewModel.getWhatToDoInfo()
        viewModel.whatToDoListDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {
                   val whatToDoList = state.data.split(",")
                    for (i in whatToDoList){
                        binding.changeChipGroup.addView(Chip(this).apply {
                            text = i
                            isCheckable = true
                            setOnClickListener{ finalWhatToDo = i }
                        })
                    }


                }
                is UiState.Failure -> {
                    Toast.makeText(this, "failurea", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun monthObserver(whatToDoText :String, count: FieldValue) {
        viewModel.updateWhatToDoMonthInfo(whatToDoText, count)
        viewModel.whatToDoMonthDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {

                }
                is UiState.Failure -> {
                    Toast.makeText(this, "failurell", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun yearObserver(whatToDoText :String, count: FieldValue) {
        viewModel.updateWhatToDoYearInfo(whatToDoText, count)
        viewModel.whatToDoYearDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {

                }
                is UiState.Failure -> {
                    Toast.makeText(this, "failurejk", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}