package com.goingbacking.goingbacking.UI.Input

import androidx.lifecycle.*
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.Repository.Input.InputRepositoryIF
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

    fun addFirstInput(userNickName: String) = viewModelScope.launch {
        _addFirstInput.value = UiState.Loading
        inputRepository.addFirstInput(userNickName) {
            _addFirstInput.postValue(it)
        }
    }

    // --- SecondInputFragment ---
    private val _updateSecondInput = MutableLiveData<UiState<String>>()

    fun updateSecondInput(userType: String) = viewModelScope.launch {
        _updateSecondInput.value = UiState.Loading
        inputRepository.updateSecondInput(userType) {
            _updateSecondInput.postValue(it)
        }
    }

    // --- ThirdInputFragment ---
    private val _updateThirdInput = MutableLiveData<UiState<String>>()
    val updateThirdInput: LiveData<UiState<String>>
        get() = _updateThirdInput

    fun updateThirdInput(selected :List<String>) = viewModelScope.launch {
        _updateThirdInput.value = UiState.Loading
        inputRepository.updateThirdInput(selected) {
            _updateThirdInput.postValue(it)
        }
    }

    // --- InputBottomSheet ---
    private val _checkUserInfo = MutableLiveData<UiState<UserInfoDTO>>()
    val checkUserInfo: LiveData<UiState<UserInfoDTO>>
        get() = _checkUserInfo

    fun checkInput() = viewModelScope.launch {
        _checkUserInfo.value = UiState.Loading
        inputRepository.checkInput {
            _checkUserInfo.postValue(it)
        }
    }

    // --- InputBottomSheet ---
    // month whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitMonthDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoMonthTime(whatToDoMonthDTO: WhatToDoMonthDTO) = viewModelScope.launch {
        _whatToDoInitMonthDTOs.value = UiState.Loading
        inputRepository.addInitWhatToDoMonthTime(whatToDoMonthDTO) {
            _whatToDoInitMonthDTOs.postValue(it)
        }
    }

    // --- InputBottomSheet ---
    // year whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitYearDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoYearTime(whatToDoYearDTO: WhatToDoYearDTO) = viewModelScope.launch {
        _whatToDoInitYearDTOs.value = UiState.Loading
        inputRepository.addInitWhatToDoYearTime(whatToDoYearDTO) {
            _whatToDoInitYearDTOs.postValue(it)
        }
    }

}