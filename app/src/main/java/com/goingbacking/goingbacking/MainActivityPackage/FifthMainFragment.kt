package com.goingbacking.goingbacking.MainActivityPackage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentFifthMainBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FifthMainFragment : Fragment() {
    lateinit var binding: FragmentFifthMainBinding
    val viewModel: MainViewModel by viewModels()


    // View를 생성하는 function
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFifthMainBinding.inflate(layoutInflater)
        binding.changeInfoTextView.setOnClickListener {
            moveChangeInfoActivity()
        }

        if(this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentFifthMainBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    // 생성된 View를 처리하는 function
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

    }

    private fun observer() {
        viewModel.getFifthUserInfo()
        viewModel.userInfoDTO.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.myNickNameTextView.text = state.data.userNickName
                    binding.myTypeTextView.text = state.data.userType
                    binding.myWhatToDoTextView.text = state.data.whatToDo
                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }

        }
    }


    fun moveChangeInfoActivity() {
        val intent : Intent? = Intent(activity, ChangeInfoActivity::class.java)
        intent?.putExtra("userNickName", "")
        intent?.putExtra("userType", "userType")
        intent?.putExtra("whatToDo", "whatToDo")
        startActivity(intent)
    }


}