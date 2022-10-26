package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.*
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.Repository.InputRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor (
    val inputRepository: InputRepositoryIF
    ) : ViewModel() {

    // --- FirstInputFragment ---
    private val _addFirstInput = MutableLiveData<UiState<String>>()
    val addFirstInput: LiveData<UiState<String>>
        get() = _addFirstInput

    fun addFirstInput(userInfoDTO: UserInfoDTO) = viewModelScope.launch {
        _addFirstInput.value = UiState.Loading
        inputRepository.addFirstInput(userInfoDTO) {
            _addFirstInput.value = it
        }
    }

    // --- SecondInputFragment ---
    private val _updateSecondInput = MutableLiveData<UiState<String>>()
    val updateSecondInput: LiveData<UiState<String>>
        get() = _updateSecondInput

    fun updateSecondInput(userType: String) = viewModelScope.launch {
        _updateSecondInput.value = UiState.Loading
        inputRepository.updateSecondInput(userType) {
            _updateSecondInput.value = it
        }
    }

    // --- ThirdInputFragment ---
    private val _updateThirdInput = MutableLiveData<UiState<String>>()
    val updateThirdInput: LiveData<UiState<String>>
        get() = _updateThirdInput

    fun updateThirdInput(whatToDo: String) = viewModelScope.launch {
        _updateSecondInput.value = UiState.Loading
        inputRepository.updateThirdInput(whatToDo) {
            _updateSecondInput.value = it
        }
    }

    // --- InputBottomSheet ---
    private val _checkUserInfo = MutableLiveData<UiState<UserInfoDTO>>()
    val checkUserInfo: LiveData<UiState<UserInfoDTO>>
        get() = _checkUserInfo

    fun checkInput() = viewModelScope.launch {
        _checkUserInfo.value = UiState.Loading
        inputRepository.checkInput {
            _checkUserInfo.value = it
        }
    }

    // --- InputBottomSheet ---
    // month whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitMonthDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoMonthTime(whatToDoMonthDTO: WhatToDoMonthDTO) = viewModelScope.launch {
        _whatToDoInitMonthDTOs.value = UiState.Loading
        inputRepository.addInitWhatToDoMonthTime(whatToDoMonthDTO) {
            _whatToDoInitMonthDTOs.value = it
        }
    }

    // --- InputBottomSheet ---
    // year whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitYearDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoYearTime(whatToDoYearDTO: WhatToDoYearDTO) = viewModelScope.launch {
        _whatToDoInitYearDTOs.value = UiState.Loading
        inputRepository.addInitWhatToDoYearTime(whatToDoYearDTO) {
            _whatToDoInitYearDTOs.value = it
        }
    }

}