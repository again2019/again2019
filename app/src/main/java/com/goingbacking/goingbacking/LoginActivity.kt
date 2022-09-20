package com.goingbacking.goingbacking

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.goingbacking.goingbacking.InputActivityPackage.FirstInputActivity
import com.goingbacking.goingbacking.ViewModel.LoginViewModel
import com.goingbacking.goingbacking.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    //mvvn 패턴으로 안 짠 곳
    private var auth : FirebaseAuth? = null


    // mvvn 패턴으로 짠 곳
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private val viewModel: LoginViewModel by viewModels()
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setGoogleLogin()

        //
        auth = FirebaseAuth.getInstance()

        email_login_button.setOnClickListener {
            signinAndSignup()
        }
        //

        viewModel.userLiveData.observe(this) {

        }
        binding.loginButton.setOnClickListener {
            login()
        }
        getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.getUser(account.idToken!!)
                    moveFirstInputPage(auth?.currentUser)

                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT)
                        .show()

                } catch (e: ApiException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun signinAndSignup() {
        auth?.createUserWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())
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
        auth?.signInWithEmailAndPassword(email_edittext.text.toString(),password_edittext.text.toString())
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

    //mvvn 패턴으로 안짬

     //이미 로그인 시 자동 로그인 하는 부분
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        // 이미 로그인 한 사용자가 있는 경우
        if (auth?.currentUser != null) {
            moveMainPage(auth?.currentUser)
        } else if (account != null) {
            moveMainPage(auth?.currentUser)
        }

    }
    fun moveMainPage(user:FirebaseUser?){
        if(user != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    private fun login() {
        getResult.launch(googleSignInClient.signInIntent)
    }

    private fun setGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1036649010261-m7q5r8e2j04bqt3gnnvne9elvvetsf41.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}