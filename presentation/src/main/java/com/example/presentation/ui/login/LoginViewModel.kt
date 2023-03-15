package com.example.presentation.ui.login

import androidx.lifecycle.*
import com.example.domain.usecase.myAccount.*
import com.example.domain.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getGsoUseCase: GetGsoUseCase,
    private val signInWithCredentialUseCase: SignInWithCredentialUseCase,
    private val loginEmailUseCase: LoginEmailUseCase,
    private val registerEmailUseCase: RegisterEmailUseCase,
    private val findEmailPasswordUseCase: FindEmailPasswordUseCase,
    private val getCurrentSession: GetCurrentSessionUseCase,
) : ViewModel() {

    // GSO를 받아오는 코드
    private val _gso = MutableLiveData<UiState<GoogleSignInOptions>>()
    val gso: LiveData<UiState<GoogleSignInOptions>>
        get() = _gso

    fun getGSO() {
        getGsoUseCase(viewModelScope) {
            _gso.postValue(it)
        }
    }


    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun emailRegister(email: String, password:String) {
        registerEmailUseCase(viewModelScope, email, password) {
            _register.postValue(it)
        }
    }

    private val _emailLogin = MutableLiveData<UiState<String>>()
    val emailLogin: LiveData<UiState<String>>
        get() = _emailLogin

    fun emailLogin(email: String, password: String) {
        loginEmailUseCase(viewModelScope, email, password) {
            _emailLogin.postValue(it)
        }
    }


    private val _forgotPassword = MutableLiveData<UiState<String>>()
    val forgotPassword: LiveData<UiState<String>>
        get() = _forgotPassword

    fun emailForgetPassword(email: String) {
        findEmailPasswordUseCase(viewModelScope, email) {
            _forgotPassword.postValue(it)
        }
    }

    private val _loginCredential = MutableLiveData<UiState<String>>()
    val loginCredential: LiveData<UiState<String>>
        get() = _loginCredential

    fun signInWithCredential(token :String) {
        signInWithCredentialUseCase(viewModelScope, token) {
            _loginCredential.postValue(it)
        }
    }

    // 로그인한 상태인지 알려주는 코드
    private val _currentSession = MutableLiveData<UiState<String>>()
    val currentSession: LiveData<UiState<String>>
        get() = _currentSession

    fun getCurrentSession() {
        getCurrentSession(viewModelScope) {
            _currentSession.postValue(it)
        }
    }













}