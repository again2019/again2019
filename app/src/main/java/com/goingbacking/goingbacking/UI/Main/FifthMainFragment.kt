package com.goingbacking.goingbacking.UI.Main

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Main.Fifth.ChangeInfoActivity
//import com.goingbacking.goingbacking.UI.Main.FifthMainFragmentDirections.ActionFifthMainFragmentToChangeInfoActivity
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentFifthMainBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FifthMainFragment : BaseFragment<FragmentFifthMainBinding>() {
    val viewModel: MainViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFifthMainBinding {
        return FragmentFifthMainBinding.inflate(inflater, container, false)
    }

    // 생성된 View를 처리하는 function
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        binding.changeInfoTextView.setOnClickListener {
            var nickName = binding.myNickNameTextView.text.toString()
            var userType = binding.myTypeTextView.text.toString()
            var whatToDo = binding.myWhatToDoTextView.text.toString()
            val directions = FifthMainFragmentDirections.actionFifthMainFragmentToChangeInfoActivity()
            findNavController().navigate(directions)
        }
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
}