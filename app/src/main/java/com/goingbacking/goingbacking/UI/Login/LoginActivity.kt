package com.goingbacking.goingbacking.UI.Login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.goingbacking.goingbacking.InputActivityPackage.FirstInputActivity
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.UI.Base.BaseActivity
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
import com.goingbacking.goingbacking.databinding.ActivityLoginBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>({
    ActivityLoginBinding.inflate(it)
}) {

    val viewModel: LoginViewModel by viewModels()
    private var auth = FirebaseAuth.getInstance()


    private lateinit var getResult: ActivityResultLauncher<Intent>

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        googleObserver()
        //setGoogleLogin()

        binding.emailLoginButton.setOnClickListener {
            signinAndSignup()
        }

        binding.loginButton.setOnClickListener {
            googleLogin()
        }


        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                try {
                    moveFirstInputPage(auth?.currentUser)

                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT)
                        .show()

                } catch (e: ApiException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun googleObserver() {
        viewModel.getGSO()
        viewModel.gso.observe(this) {
            state ->
                when (state) {
                    is UiState.Success -> {
                        googleSignInClient = getClient(this, state.data)

                    }

                }
        }
    }

    private fun googleLogin() {
        getResult.launch(googleSignInClient.signInIntent)
    }

//    private fun setGoogleLogin() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("1036649010261-p08hat5d9stl7qvdun1mg4fv94kj8nt6.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//        googleSignInClient = getClient(this, gso)
//    }
    private fun signinAndSignup() {
        auth?.createUserWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.passwordEdittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    //Creating a user account
                    moveFirstInputPage(task.result?.user)
                }else if(task.exception?.message.isNullOrEmpty()){
                    //Show the error message
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }else{
                    //Login if you have account
                    signinEmail()
                }
            }
        }

    fun signinEmail(){
        auth?.signInWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.passwordEdittext.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful){
                    //Login
                    moveFirstInputPage(task.result?.user)
                }else{
                    //Show the error message
                    Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }




    private fun moveFirstInputPage(user: FirebaseUser?) {
        if (user != null) {
            val intent : Intent? = Intent(this, FirstInputActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



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
    fun moveMainPage(user:FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}