package com.goingbacking.goingbacking.UI.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Input.InputActivity
import com.goingbacking.goingbacking.UI.Main.MainActivity
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
import com.goingbacking.goingbacking.databinding.FragmentLoginBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            googleLoginObserver()
        }
        passwordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetFrgament)
        }
    }



    // 구글 로그인 코드

    private fun googleLoginObserver() {
        viewModel.getGSO()
        viewModel.gso.observe(viewLifecycleOwner) {
                state ->
            when (state) {
                is UiState.Success -> {
                    googleSignInClient = getClient(requireActivity(), state.data)
                    googleSignInClient?.signInIntent?.run {
                        startActivityForResult(this, REQUEST_CODE)
                    }
                }
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), R.string.login_fail, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val signInTask = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = signInTask.getResult(ApiException::class.java)
                onGoogleSignInAccount(account)
            } catch (e: ApiException) {
                Toast.makeText(requireActivity(), R.string.login_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onGoogleSignInAccount(account: GoogleSignInAccount?) {
        if (account != null) {
            viewModel.signInWithCredential(account.idToken!!)
            viewModel.loginCredential.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Success -> {
                        binding.progressCircular.hide()
                        Toast.makeText(requireActivity(), R.string.login_success, Toast.LENGTH_SHORT).show()
                        // Google로 로그인 성공
                        moveInputPage()
                    }
                    is UiState.Loading -> {
                        binding.progressCircular.show()
                    }
                    is UiState.Failure -> {
                        binding.progressCircular.hide()
                        Toast.makeText(requireActivity(), R.string.login_fail, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // 구글 로그인 코드

    // email 로그인 코드
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
                    binding.progressCircular.hide()
                    Toast.makeText(requireActivity(), R.string.login_success, Toast.LENGTH_SHORT).show()
                    moveInputPage()
                }
                is UiState.Loading -> {
                    binding.progressCircular.isIndeterminate = true
                    binding.progressCircular.show()
                }
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                    Toast.makeText(requireActivity(), R.string.login_fail, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // email 로그인 코드
    // 유효한지를 판단하는 기준
    fun validation(): Boolean = with(binding) {
            var isValid = true

            if (emailEdittext.text.isNullOrEmpty()){
                isValid = false
                Toast.makeText(requireActivity(), R.string.email_again, Toast.LENGTH_SHORT).show()

            }else{
                if (!emailEdittext.text.toString().isValidEmail()){
                    isValid = false
                    Toast.makeText(requireActivity(), R.string.email_invalid, Toast.LENGTH_SHORT).show()

                }
            }
            if (passwordEdittext.text.isNullOrEmpty()){
                isValid = false
                Toast.makeText(requireActivity(), R.string.password_again, Toast.LENGTH_SHORT).show()


            }else{
                if (passwordEdittext.text.toString().length < 8){
                    isValid = false
                    Toast.makeText(requireActivity(), R.string.password_again, Toast.LENGTH_SHORT).show()
                }
            }
            return isValid
        }

    override fun onStart() {
        super.onStart()

        Toast.makeText(requireContext(), FirebaseAuth.getInstance().currentUser?.uid, Toast.LENGTH_SHORT).show()
        viewModel.getCurrentSession()
        viewModel.currentSession.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.progressCircular.hide()
                    moveMainPage()
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
            }
        }
    }

    private fun moveInputPage() {
        val intent = Intent(requireActivity(), InputActivity::class.java)
        startActivity(intent)
    }


    private fun moveMainPage() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        finishAffinity(requireActivity())
    }


    companion object {
        private const val REQUEST_CODE = 1
    }
}



