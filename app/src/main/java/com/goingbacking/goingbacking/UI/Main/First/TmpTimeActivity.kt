package com.goingbacking.goingbacking.UI.Main.First

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.goingbacking.goingbacking.Adapter.TmpTimeRecyclerViewAdapter
import com.goingbacking.goingbacking.UI.Base.BaseActivity

import com.goingbacking.goingbacking.ViewModel.TmpTimeViewModel
import com.goingbacking.goingbacking.databinding.ActivityTmpTimeBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TmpTimeActivity : BaseActivity<ActivityTmpTimeBinding>({
    ActivityTmpTimeBinding.inflate(it)
}) {

    val adapter by lazy {
        TmpTimeRecyclerViewAdapter(
            onItemClicked = { wakeUpTime1, wakeUpTime2, wakeUpTime3, wakeUpTime4, count, count_double ->
                TmpTimeDayOberver(wakeUpTime1, wakeUpTime2, count)
                TmpTimeMonthOberver(wakeUpTime3, wakeUpTime2, count)
                TmpTimeYearOberver(wakeUpTime3, count)
                val intent = Intent(this, WhatToDoSaveActivity::class.java)
                intent.putExtra("count", count_double)
                startActivity(intent)
            }

        )
    }
    val viewModel : TmpTimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TmpTimeOberver()

        binding.TmprecyclerView.layoutManager = LinearLayoutManager(this)
        binding.TmprecyclerView.adapter = adapter

    }


    private fun TmpTimeOberver(){
        viewModel.getTmpTimeInfo()
        viewModel.tmpTimeDTOs.observe(this) { state ->
            when(state){
                is UiState.Success -> {
                    adapter.updateList(state.data)
                }
                is UiState.Failure -> {
                }

            }
        }
    }

    private fun TmpTimeDayOberver(wakeUpTime1: String, wakeUpTime2: String, count: FieldValue){
        viewModel.updateTmpTimeDayInfo(wakeUpTime1, wakeUpTime2, count)
        viewModel.tmpTimeDayDTOs.observe(this) { state ->
            when(state){
                is UiState.Success -> {

                }
                is UiState.Failure -> {
                    Toast.makeText(this, "failure1", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun TmpTimeMonthOberver(wakeUpTime1: String, wakeUpTime2: String, count: FieldValue){
        viewModel.updateTmpTimeMonthInfo(wakeUpTime1, wakeUpTime2, count)
        viewModel.tmpTimeMonthDTOs.observe(this) { state ->
            when(state){
                is UiState.Success -> {

                }
                is UiState.Failure -> {
                    Toast.makeText(this, "failure2", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun TmpTimeYearOberver(wakeUpTime: String, count: FieldValue){
        viewModel.updateTmpTimeYearInfo(wakeUpTime, count)
        viewModel.tmpTimeYearDTOs.observe(this) { state ->
            when(state){
                is UiState.Success -> {

                }
                is UiState.Failure -> {
                    Toast.makeText(this, "failure3", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


}