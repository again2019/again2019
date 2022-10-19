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

}