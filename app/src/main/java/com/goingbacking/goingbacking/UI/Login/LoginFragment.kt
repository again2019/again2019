package com.goingbacking.goingbacking.UI.Login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    val viewModel: LoginViewModel by viewModels()
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var googleSignInClient: GoogleSignInClient
    private var auth = FirebaseAuth.getInstance()

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


//    private fun signinAndSignup() {
//        auth?.createUserWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.passwordEdittext.text.toString())
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful){
//                    //Creating a user account
//                    moveFirstInputPage(task.result?.user)
//                }else if(task.exception?.message.isNullOrEmpty()){
//                    //Show the error message
//                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
//                }else{
//                    //Login if you have account
//                    signinEmail()
//                }
//            }
//        }
//
//    fun signinEmail(){
//        auth?.signInWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.passwordEdittext.text.toString())
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful){
//                    //Login
//                    moveFirstInputPage(task.result?.user)
//                }else{
//                    //Show the error message
//                    Toast.makeText(requireActivity(),task.exception?.message,Toast.LENGTH_LONG).show()
//                }
//            }
//    }




//    override fun onStart() {
//        super.onStart()
//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        // 이미 로그인 한 사용자가 있는 경우
//        if (auth?.currentUser != null) {
//            moveMainPage(auth?.currentUser)
//        } else if (account != null) {
//            moveMainPage(auth?.currentUser)
//        }
//
//    }
//    fun moveMainPage(user:FirebaseUser?){
//        if(user != null){
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }

