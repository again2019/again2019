package com.goingbacking.goingbacking.ui.main.first

import android.content.Intent
import android.os.Bundle

import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.adapter.TmpTimeRecyclerViewAdapter
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.service.AlarmService
import com.goingbacking.goingbacking.ui.base.BaseActivity

import com.goingbacking.goingbacking.databinding.ActivityTmpTimeBinding
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TmpTimeActivity : BaseActivity<ActivityTmpTimeBinding>({
    ActivityTmpTimeBinding.inflate(it)
}) {
    val adapter by lazy {
        TmpTimeRecyclerViewAdapter ()
    }
    val viewModel : FirstViewModel by viewModels()

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
        viewModel.getTmpTimeInfo()
        viewModel.tmpTimeDTOs.observe(this) { state ->
            when(state){
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    adapter.submitList(state.data)
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



}