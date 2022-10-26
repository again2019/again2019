package com.goingbacking.goingbacking.UI.Main

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Login.LoginActivity
import com.goingbacking.goingbacking.UI.Main.Fifth.ChangeInfoActivity
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
//import com.goingbacking.goingbacking.UI.Main.FifthMainFragmentDirections.ActionFifthMainFragmentToChangeInfoActivity
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentFifthMainBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FifthMainFragment : BaseFragment<FragmentFifthMainBinding>() {
    val viewModel1: MainViewModel by viewModels()
    val viewModel2: LoginViewModel by viewModels()
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
        onClick()
    }


    private fun observer() {
        viewModel1.getFifthUserInfo()
        viewModel1.userInfoDTO.observe(viewLifecycleOwner) { state ->
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

    private fun onClick() = with(binding) {
        changeInfoTextView.setOnClickListener {
            var nickName = binding.myNickNameTextView.text.toString()
            var userType = binding.myTypeTextView.text.toString()
            var whatToDo = binding.myWhatToDoTextView.text.toString()

            val intent = Intent(requireContext(), ChangeInfoActivity::class.java)
            intent.putExtra("nickName", nickName)
            intent.putExtra("userType", userType)
            intent.putExtra("whatToDo", whatToDo)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            viewModel2.logout()
            viewModel2.logout.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Success -> {
                        toast(requireContext(), getString(R.string.logout_success))
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity(requireActivity())

                    }
                }
            }
        }
    }

}