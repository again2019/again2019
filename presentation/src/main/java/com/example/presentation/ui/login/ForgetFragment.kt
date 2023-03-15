package com.example.presentation.ui.login

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.util.UiState
import com.example.presentation.ui.base.BaseFragment

import com.example.domain.util.isValidEmail
import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentForgetBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgetFragment : BaseFragment<FragmentForgetBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgetBinding {
        return FragmentForgetBinding.inflate(inflater)

        // inflate: layout id를 알고 있는 경우
        // databinding: layout id를 모르고 있는 경우우
    }

    val viewModel : LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_forgotFragment_to_emailLoginFragment)
        }

        binding.forgotPassBtn.setOnClickListener {
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                if (validation()) {
                    observer()
                }
            }
        }
    }

    private fun observer()  {
        viewModel.emailForgetPassword(binding.emailEdittext.text.toString())
        viewModel.forgotPassword.observe(viewLifecycleOwner) {
            state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.email_link_send_fail))
                }
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.email_link_send_success))
                    findNavController().navigate(R.id.action_forgotFragment_to_emailLoginFragment)
                }
            }
        }
    }

    fun validation(): Boolean = with(binding) {
        var isValid = true

        if (emailEdittext.text.isNullOrEmpty()){
            isValid = false
            toast(requireContext(), getString(R.string.email_again))

        } else{
            if (!emailEdittext.text.toString().isValidEmail()){
                isValid = false
                toast(requireContext(), getString(R.string.email_again))
            }
        }

        return isValid
    }


}