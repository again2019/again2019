package com.goingbacking.goingbacking.UI.Login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
import com.goingbacking.goingbacking.databinding.FragmentLoginBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    val viewModel: LoginViewModel by viewModels()
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleLoginObserver()

        buttonClick()
    }

    // 버튼 이벤트 모음
    private fun buttonClick() = with(binding) {
        registerButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        emailLoginButton.setOnClickListener {
            emailLogin()

        }
        loginButton.setOnClickListener {
            googleLogin()
        }
        passwordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetFrgament)
        }
    }


    // 구글 로그인
    private fun googleLogin() {
        getResult.launch(googleSignInClient.signInIntent)

    }

    private fun googleLoginObserver() {
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                try {
                    findNavController().navigate(R.id.action_loginFragment_to_input_navigation)


                    Toast.makeText(requireActivity(), "구글 로그인 성공", Toast.LENGTH_SHORT).show()

                } catch (e: ApiException) {
                    Toast.makeText(requireActivity(), e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getGSO()
        viewModel.gso.observe(viewLifecycleOwner) {
            state ->
                when (state) {
                    is UiState.Success -> {

                        googleSignInClient = getClient(requireActivity(), state.data)
                    }
                    is UiState.Failure -> {
                        Toast.makeText(requireActivity(), "구글 로그인 실패", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }


    // email login
    private fun emailLogin() = with(binding) {
        if (validation()) {
            viewModel.emailLogin(emailEdittext.text.toString(), passwordEdittext.text.toString())
            emailLoginObserver()

        }
    }

    private fun emailLoginObserver() {
        viewModel.emailLogin.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    Toast.makeText(requireActivity(), "로그인 성공", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_input_navigation)

                }
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 유효한지를 판단하는 기준
    fun validation(): Boolean = with(binding) {
            var isValid = true

            if (emailEdittext.text.isNullOrEmpty()){
                isValid = false

            }else{
                if (!emailEdittext.text.toString().isValidEmail()){
                    isValid = false
                }
            }
            if (passwordEdittext.text.isNullOrEmpty()){
                isValid = false

            }else{
                if (passwordEdittext.text.toString().length < 8){
                    isValid = false
                }
            }
            return isValid
        }
    }



