package com.goingbacking.goingbacking.UI.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
import com.goingbacking.goingbacking.databinding.FragmentRegisterBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*

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

        registerObserver()
        buttonClick()

    }

    private fun registerObserver() {
        viewModel.register.observe(viewLifecycleOwner) {state ->
            when(state) {
                is UiState.Loading -> {
                    binding.registerProgress.visibility
                }
                is UiState.Success -> {
                    Toast.makeText(requireActivity(), "회원 가입 성공", Toast.LENGTH_SHORT)
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), "회원 가입 실패", Toast.LENGTH_SHORT)
                }
             }
        }


    }

    private fun buttonClick() = with(binding) {

            registerButton.setOnClickListener {
                if (validation()) {
                    viewModel.emailRegister(registerEmailEdittext.text.toString(), registerPasswordEdittext1.text.toString())
                }
            }

    }

    fun validation(): Boolean = with(binding) {
        var isValid = true


        if (registerEmailEdittext.text.isNullOrEmpty()){
            isValid = false
            Toast.makeText(requireActivity(), "email을 다시 입력하세요", Toast.LENGTH_SHORT).show()
        } else {
            if (!registerEmailEdittext.text.toString().isValidEmail()) {
                isValid = false
                Toast.makeText(requireActivity(), "email이 유효하지 않아요", Toast.LENGTH_SHORT).show()

            }

        }

        if (registerPasswordEdittext1.text.isNullOrEmpty()){
            isValid = false
            Toast.makeText(requireActivity(), "password를 다시 입력하세요", Toast.LENGTH_SHORT).show()
        } else{
            if (registerPasswordEdittext1.text.toString().length < 8){
                isValid = false
                Toast.makeText(requireActivity(), "password가 너무 짧아요. 다시 입력하세요", Toast.LENGTH_SHORT).show()
            } else if (registerPasswordEdittext1.text != registerPasswordEdittext2.text) {
                Toast.makeText(requireActivity(), "password를 다시 확인해주세요", Toast.LENGTH_SHORT).show()

            }
        }
        return isValid
    }

    fun String.isValidEmail() =
        isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()




}