package com.again.again2019.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.again.again2019.LoginActivity
import com.again.again2019.MainActivity
import com.again.again2019.Repository.LoginRepository
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private var loginRepository: LoginRepository = LoginRepository()
    private val _userLiveData =  loginRepository.userLiveData

    val userLiveData: LiveData<FirebaseUser>
    get() = _userLiveData

    fun getUser(idToken: String) {
        loginRepository.getUser(idToken)
    }


}