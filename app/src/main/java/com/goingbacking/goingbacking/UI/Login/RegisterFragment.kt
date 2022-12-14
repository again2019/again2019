package com.goingbacking.goingbacking.UI.Login

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentRegisterBinding
import com.goingbacking.goingbacking.util.NetworkManager
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val viewModel : LoginViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backbtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        onClick()
    }

    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.sign_up_success))
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    toast(requireContext(), getString(R.string.sign_up_fail))
                }
             }
        }
    }

    private fun onClick() = with(binding) {

            registerButton.setOnClickListener {
                if (!NetworkManager.checkNetworkState(requireContext())) {
                    toast(requireContext(), getString(R.string.network_fail))
                } else {
                    if (validation()) {
                        viewModel.emailRegister(
                            registerEmailEdittext.text.toString(),
                            registerPasswordEdittext1.text.toString()
                        )
                        observer()
                    }
                }
            }

    }

    fun validation(): Boolean = with(binding) {
        var isValid = true

        if (registerEmailEdittext.text.isNullOrEmpty()){
            isValid = false
            toast(requireContext(), getString(R.string.email_again))
        } else {
            if (!registerEmailEdittext.text.toString().isValidEmail()) {
                isValid = false
                toast(requireContext(), getString(R.string.email_invalid))
            }

        }

        if (registerPasswordEdittext1.text.isNullOrEmpty()){
            isValid = false
            toast(requireContext(), getString(R.string.password_again))
        } else{
            if (registerPasswordEdittext1.text.toString().length < 10){
                isValid = false
                toast(requireContext(), getString(R.string.password_again_short))
            }
            if (registerPasswordEdittext1.text.toString() != registerPasswordEdittext2.text.toString()) {
                toast(requireContext(), getString(R.string.password_correct))
            }
        }
        return isValid
    }




}