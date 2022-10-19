package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.*
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Repository.InputRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor (
    val inputRepository: InputRepositoryIF
    ) : ViewModel() {

    private val _addFirstInput = MutableLiveData<UiState<String>>()
    val addFirstInput: LiveData<UiState<String>>
        get() = _addFirstInput

    private val _updateSecondInput = MutableLiveData<UiState<String>>()
    val updateSecondInput: LiveData<UiState<String>>
        get() = _updateSecondInput

    private val _updateThirdInput = MutableLiveData<UiState<String>>()
    val updateThirdInput: LiveData<UiState<String>>
        get() = _updateThirdInput

    fun addFirstInput(userInfoDTO: UserInfoDTO) = viewModelScope.launch {
        _addFirstInput.value = UiState.Loading
        inputRepository.addFirstInput(userInfoDTO) {
            _addFirstInput.value = it
        }
    }

    fun updateSecondInput(userType: String) = viewModelScope.launch {
        _updateSecondInput.value = UiState.Loading
        inputRepository.updateSecondInput(userType) {
            _updateSecondInput.value = it
        }

    }

    fun updateThirdInput(whatToDo: String) = viewModelScope.launch {
        _updateSecondInput.value = UiState.Loading
        inputRepository.updateThirdInput(whatToDo) {
            _updateSecondInput.value = it
        }

    }


}