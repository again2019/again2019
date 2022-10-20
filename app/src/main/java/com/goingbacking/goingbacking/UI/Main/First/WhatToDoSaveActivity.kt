package com.goingbacking.goingbacking.UI.Main.First

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.ViewModel.TmpTimeViewModel
import com.goingbacking.goingbacking.databinding.ActivityWhatToDoSaveBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WhatToDoSaveActivity : BaseActivity<ActivityWhatToDoSaveBinding>({
    ActivityWhatToDoSaveBinding.inflate(it)
}) {
    val viewModel :TmpTimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val count_double = intent.getDoubleExtra("count", 0.0)
        Log.d("experiment", count_double.toString())

        binding.whatToDoSaveButton.setOnClickListener {
            Log.d("experiment", binding.whatToDoEditText.text.toString())
            monthObserver(binding.whatToDoEditText.text.toString(), FieldValue.increment(count_double))
            yearObserver(binding.whatToDoEditText.text.toString(), FieldValue.increment(count_double))
        }

    }

    private fun monthObserver(whatToDoText :String, count: FieldValue) {
        viewModel.updateWhatToDoMonthInfo(whatToDoText, count)
        viewModel.whatToDoMonthDTOs.observe(this) { state ->
            when(state) {
                is UiState.Success -> {

                }
                is UiState.Failure -> {
                    Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}