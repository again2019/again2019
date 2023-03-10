package com.goingbacking.goingbacking.ui.input

import androidx.lifecycle.*
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel
import com.example.domain.usecase.whatToDo.AddWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.AddWhatToDoYearUseCase
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.*
import com.goingbacking.goingbacking.repository.input.InputRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor (
    private val whatToDoMonthUseCase: AddWhatToDoMonthUseCase,
    private val whatToDoYearUseCase: AddWhatToDoYearUseCase,
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

    fun addInitWhatToDoMonthTime(whatToDoMonthModel: WhatToDoMonthModel) = viewModelScope.launch {
        whatToDoMonthUseCase(viewModelScope, whatToDoMonthModel) {
            _whatToDoInitMonthDTOs.value = it
        }
    }

    // --- InputBottomSheet ---
    // year whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitYearDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoYearTime(whatToDoYearModel: WhatToDoYearModel) = viewModelScope.launch {
        whatToDoYearUseCase(viewModelScope, whatToDoYearModel) {
            _whatToDoInitYearDTOs.value = it
        }
    }

    private val _InitRankMonthDTOs = MutableLiveData<UiState<String>>()

    fun addInitRankMonthTime(rankMonthDTO: NewSaveTimeMonthDTO) = viewModelScope.launch {
        _InitRankMonthDTOs.value = UiState.Loading
        inputRepository.addInitRankMonthTime(rankMonthDTO) {
            _InitRankMonthDTOs.postValue(it)
        }
    }

    private val _InitRankYearDTOs = MutableLiveData<UiState<String>>()

    fun addInitRankYearTime(rankYearDTO: NewSaveTimeYearDTO) = viewModelScope.launch {
        _InitRankYearDTOs.value = UiState.Loading
        inputRepository.addInitRankYearTime(rankYearDTO) {
            _InitRankYearDTOs.postValue(it)
        }
    }

}