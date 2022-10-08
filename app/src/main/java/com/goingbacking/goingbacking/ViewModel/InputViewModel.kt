package com.goingbacking.goingbacking.ViewModel

import android.app.Application
import androidx.lifecycle.*
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Repository.InputRepository
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

    fun addFirstInput(userInfoDTO: UserInfoDTO) {
        _addFirstInput.value = UiState.Loading
        inputRepository.addFirstInput(userInfoDTO) {
            _addFirstInput.value = it
        }
    }

    fun updateSecondInput(userType: String) {
        _updateSecondInput.value = UiState.Loading
        inputRepository.updateSecondInput(userType) {
            _updateSecondInput.value = it
        }

    }


}