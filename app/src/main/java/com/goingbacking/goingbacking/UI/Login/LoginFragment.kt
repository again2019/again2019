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
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.isValidEmail
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    val viewModel: LoginViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient
    private  var firebaseAuth = FirebaseAuth.getInstance()

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
                    Toast.makeText(requireActivity(), "구글 로그인 실패", Toast.LENGTH_SHORT).show()
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
                e.printStackTrace()
            }
        }
    }

    private fun onGoogleSignInAccount(account: GoogleSignInAccount?) {
        if (account != null) {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)

            // **** mvvn 패턴화 필요 ****
            firebaseAuth.signInWithCredential(credential)?.addOnCompleteListener {
                onFirebaseAuthTask(it)
            // --------------------------


            }
        }
    }

    private fun onFirebaseAuthTask(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Toast.makeText(requireActivity(), "로그인 성공", Toast.LENGTH_SHORT).show()

            // Google로 로그인 성공
            val intent = Intent(requireActivity(), InputActivity::class.java)
            startActivity(intent)


        } else {
            // Google로 로그인 실패
            Toast.makeText(requireActivity(), "로그인 실패", Toast.LENGTH_SHORT).show()

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
                    Toast.makeText(requireActivity(), "로그인 성공", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_input_navigation)
                }
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), "로그인 실패", Toast.LENGTH_SHORT).show()
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

    override fun onStart() {
        super.onStart()

//        if(PrefUtil.firebaseUid() == null) {
//
//        } else {
//            moveMainPage()
//        }

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



