package com.goingbacking.goingbacking.UI.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentForgetBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgetFragment : BaseFragment<FragmentForgetBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgetBinding {
        return FragmentForgetBinding.inflate(inflater, container, false)
    }

    val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        binding.forgotPassBtn.setOnClickListener {
            if(validation()) {
                findNavController().navigate(R.id.action_forgotFragment_to_loginFragment)
            }
        }
    }

    private fun observer()  {
        viewModel.forgotPassword.observe(viewLifecycleOwner) {
            state ->
            when(state) {
                is UiState.Loading -> {

                }
                is UiState.Failure -> {
                    toast(requireContext(), getString(R.string.email_link_send_fail))
                }
                is UiState.Success -> {
                    toast(requireContext(), getString(R.string.email_link_send_success))
                    viewModel.emailForgetPassword(binding.emailEt.text.toString())
                }
            }
        }
    }

    fun validation(): Boolean = with(binding) {
        var isValid = true

        if (emailEt.text.isNullOrEmpty()){
            isValid = false
            toast(requireContext(), getString(R.string.email_again))

        } else{
            if (!emailEt.text.toString().isValidEmail()){
                isValid = false
                toast(requireContext(), getString(R.string.email_again))
            }
        }

        return isValid
    }


}