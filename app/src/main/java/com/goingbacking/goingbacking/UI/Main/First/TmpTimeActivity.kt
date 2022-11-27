package com.goingbacking.goingbacking.UI.Main.First

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.goingbacking.goingbacking.Adapter.TmpTimeRecyclerViewAdapter
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.Service.AlarmService
import com.goingbacking.goingbacking.UI.Base.BaseActivity

import com.goingbacking.goingbacking.databinding.ActivityTmpTimeBinding
import com.goingbacking.goingbacking.util.UiState
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