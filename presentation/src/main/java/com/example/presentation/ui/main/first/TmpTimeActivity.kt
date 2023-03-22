package com.example.presentation.ui.main.first
import androidx.activity.viewModels
import android.content.Intent
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.util.Response
import com.example.domain.util.UiState

import com.example.presentation.service.AlarmService
import com.example.presentation.ui.base.BaseActivity

import com.example.domain.util.toast
import com.example.presentation.R
import com.example.presentation.adapter.TmpTimeRecyclerViewAdapter
import com.example.presentation.databinding.ActivityTmpTimeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TmpTimeActivity : BaseActivity<ActivityTmpTimeBinding>({
    ActivityTmpTimeBinding.inflate(it)
}) {
    val adapter by lazy {
        TmpTimeRecyclerViewAdapter ()
    }
    private val viewModel: FirstViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.action.equals("MOVE")) {
            val intent = Intent(this, AlarmService::class.java)
            intent.action = "MOVE"
            startService(intent)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backbtn.setOnClickListener {
            finish()
        }

        TmpTimeOberver()
        observer()
        binding.TmprecyclerView.layoutManager = LinearLayoutManager(this)
        binding.TmprecyclerView.adapter = adapter

    }

    private fun observer() {
        viewModel.getFifthUserInfo()
        viewModel.userInfoDTO.observe(this) { state ->
            when(state){
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    binding.nickName.text = state.data.userNickName.toString()
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(this, getString(R.string.load_tmpTime_fail))
                }

            }
        }


    }


    private fun TmpTimeOberver(){
        viewModel.getTmpTimeModelList()
        viewModel.tmpTimeModelList.observe(this) { state ->
            when(state){
                is Response.Success -> {
                    binding.progressCircular.hide()
                    adapter.submitList(state.data)
                }
                is Response.Loading -> {
                    binding.progressCircular.show()
                }
                is Response.Failure -> {
                    binding.progressCircular.hide()
                    toast(this, getString(R.string.load_tmpTime_fail))
                }

            }
        }
    }



}