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

    fun getGSO() = viewModelScope.launch {
        _gso.value = UiState.Loading
        loginRepository.getGSO {
            _gso.value = it
        }
    }
}