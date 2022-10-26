package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.*
import com.goingbacking.goingbacking.Repository.LoginRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginRepository: LoginRepositoryIF
) : ViewModel() {
    private val _gso = MutableLiveData<UiState<GoogleSignInOptions>>()
    val gso: LiveData<UiState<GoogleSignInOptions>>
        get() = _gso

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _emailLogin = MutableLiveData<UiState<String>>()
    val emailLogin: LiveData<UiState<String>>
        get() = _emailLogin

    private val _forgotPassword = MutableLiveData<UiState<String>>()
    val forgotPassword: LiveData<UiState<String>>
        get() = _forgotPassword

    private val _loginCredential = MutableLiveData<UiState<String>>()
    val loginCredential: LiveData<UiState<String>>
        get() = _loginCredential

    private val _currentSession = MutableLiveData<UiState<String>>()
    val currentSession: LiveData<UiState<String>>
        get() = _currentSession


    // 로그 아웃
    private val _logout = MutableLiveData<UiState<String>>()
    val logout: LiveData<UiState<String>>
        get() = _logout


    fun getGSO() = viewModelScope.launch {
        _gso.value = UiState.Loading
        loginRepository.getGSO {
            _gso.value = it
        }
    }

    fun emailRegister(email: String, password:String) = viewModelScope.launch {
        _register.value = UiState.Loading
        loginRepository.emailRegister(email, password) {
            _register.value = it
        }
    }

    fun emailLogin(email: String, password: String) = viewModelScope.launch {
        _emailLogin.value = UiState.Loading
        loginRepository.emailLogin(email, password) {
            _emailLogin.value = it
        }
    }

    fun emailForgetPassword(email: String) = viewModelScope.launch {
        _forgotPassword.value = UiState.Loading
        loginRepository.emailForgetPassword(email) {
            _forgotPassword.value = it
        }
    }

    fun signInWithCredential(token :String) = viewModelScope.launch {
        _loginCredential.value = UiState.Loading
        loginRepository.signInWithCredential(token) {
            _loginCredential.value = it
        }
    }

    fun getCurrentSession() = viewModelScope.launch {
        _currentSession.value = UiState.Loading
        loginRepository.getCurrentSession() {
            _currentSession.value = it
        }
    }

    fun logout() = viewModelScope.launch {
        _logout.value = UiState.Loading
        loginRepository.logout() {
            _logout.value = it
        }
    }

}