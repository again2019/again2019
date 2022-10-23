package com.goingbacking.goingbacking.UI.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
import com.goingbacking.goingbacking.databinding.FragmentForgetBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgetFragment : BaseFragment<FragmentForgetBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgetBinding {
        return FragmentForgetBinding.inflate(inflater, container, false)
    }

    val viewModel :LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forgotPasswordObserver()
        binding.forgotPassBtn.setOnClickListener {
            if(validation()) {
                findNavController().navigate(R.id.action_forgotFragment_to_loginFragment)
            }
        }
    }

    private fun forgotPasswordObserver() = with(binding) {
        viewModel.forgotPassword.observe(viewLifecycleOwner) {
            state ->
            when(state) {
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), R.string.email_link_send_fail, Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Toast.makeText(requireActivity(), R.string.email_link_send_success, Toast.LENGTH_SHORT).show()
                    viewModel.emailForgetPassword(binding.emailEt.text.toString())
                }
            }
        }
    }

    fun validation(): Boolean  = with(binding) {
        var isValid = true

        if (emailEt.text.isNullOrEmpty()){
            isValid = false
            Toast.makeText(requireActivity(), R.string.email_again, Toast.LENGTH_SHORT).show()

        } else{
            if (!emailEt.text.toString().isValidEmail()){
                isValid = false
                Toast.makeText(requireActivity(), R.string.email_again, Toast.LENGTH_SHORT).show()

            }
        }

        return isValid
    }


}