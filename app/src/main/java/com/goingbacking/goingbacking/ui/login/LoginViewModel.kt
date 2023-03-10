package com.goingbacking.goingbacking.ui.login

import androidx.lifecycle.*
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.repository.login.LoginRepositoryIF
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginRepository: LoginRepositoryIF
) : ViewModel() {

    // GSO를 받아오는 코드
    private val _gso = MutableLiveData<UiState<GoogleSignInOptions>>()
    val gso: LiveData<UiState<GoogleSignInOptions>>
        get() = _gso

    fun getGSO() = viewModelScope.launch {
        _gso.value = UiState.Loading
        loginRepository.getGSO {
            _gso.value = it
        }
    }


    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun emailRegister(email: String, password:String) = viewModelScope.launch {
        _register.value = UiState.Loading
        loginRepository.emailRegister(email, password) {
            _register.value = it
        }
    }

    private val _emailLogin = MutableLiveData<UiState<String>>()
    val emailLogin: LiveData<UiState<String>>
        get() = _emailLogin

    fun emailLogin(email: String, password: String) = viewModelScope.launch {
        _emailLogin.value = UiState.Loading
        loginRepository.emailLogin(email, password) {
            _emailLogin.value = it
        }
    }


    private val _forgotPassword = MutableLiveData<UiState<String>>()
    val forgotPassword: LiveData<UiState<String>>
        get() = _forgotPassword

    fun emailForgetPassword(email: String) = viewModelScope.launch {
        _forgotPassword.value = UiState.Loading
        loginRepository.emailForgetPassword(email) {
            _forgotPassword.value = it
        }
    }

    private val _loginCredential = MutableLiveData<UiState<String>>()
    val loginCredential: LiveData<UiState<String>>
        get() = _loginCredential

    fun signInWithCredential(token :String) = viewModelScope.launch {
        _loginCredential.value = UiState.Loading
        loginRepository.signInWithCredential(token) {
            _loginCredential.value = it
        }
    }

    // 로그인한 상태인지 알려주는 코드
    private val _currentSession = MutableLiveData<UiState<String>>()
    val currentSession: LiveData<UiState<String>>
        get() = _currentSession

    fun getCurrentSession() = viewModelScope.launch {
        _currentSession.value = UiState.Loading
        loginRepository.getCurrentSession() {
            _currentSession.value = it
        }
    }













}