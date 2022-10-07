package com.goingbacking.goingbacking.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.LoginActivity
import com.goingbacking.goingbacking.MainActivity
import com.goingbacking.goingbacking.Repository.LoginRepository

import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    private var loginRepository: LoginRepository = LoginRepository()
    private val _userLiveData = MutableLiveData<FirebaseUser>()

    val userLiveData: LiveData<FirebaseUser>
    get() = _userLiveData

    fun getUser(idToken: String) {
        _userLiveData.postValue(loginRepository.getGoogleUser(idToken))
    }

}