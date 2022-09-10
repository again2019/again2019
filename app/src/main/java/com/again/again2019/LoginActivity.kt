package com.again.again2019

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.again.again2019.ViewModel.LoginViewModel
import com.again.again2019.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException


class LoginActivity : AppCompatActivity() {
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
        viewModel.userLiveData.observe(this) {
            binding.textView.text = it.displayName
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
                    val intent : Intent? = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT)
                        .show()

                } catch (e: ApiException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun login() {
        getResult.launch(googleSignInClient.signInIntent)
    }

    private fun setGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("792147629825-0bjemqcd45s9s8mjs8og627hul0011tn.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}