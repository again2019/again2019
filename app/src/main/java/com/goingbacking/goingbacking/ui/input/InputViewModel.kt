package com.goingbacking.goingbacking.ui.input

import androidx.lifecycle.*
import com.example.domain.model.UserInfoModel
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel
import com.example.domain.usecase.userInfo.my.AddMyUserInfoUseCase
import com.example.domain.usecase.userInfo.my.GetMyUserInfoUseCase
import com.example.domain.usecase.userInfo.my.UpdateMyUserSelectedListUseCase
import com.example.domain.usecase.userInfo.my.UpdateMyUserTypeUseCase
import com.example.domain.usecase.whatToDo.my.AddWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.my.AddWhatToDoYearUseCase
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.*
import com.goingbacking.goingbacking.repository.input.InputRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor (
    private val addMyUserInfoUseCase: AddMyUserInfoUseCase,
    private val updateMyUserTypeUseCase: UpdateMyUserTypeUseCase,
    private val updateMyUserSelectedListUseCase: UpdateMyUserSelectedListUseCase,
    private val whatToDoMonthUseCase: AddWhatToDoMonthUseCase,
    private val whatToDoYearUseCase: AddWhatToDoYearUseCase,
    private val getMyUserInfoUseCase: GetMyUserInfoUseCase,

    val inputRepository: InputRepositoryIF
    ) : ViewModel() {

    // --- FirstInputFragment ---
    private val _addFirstInput = MutableLiveData<UiState<String>>()

    fun addFirstInput(userNickName: String) {
        addMyUserInfoUseCase(viewModelScope, userNickName) {
            _addFirstInput.postValue(it)
        }
    }

    // --- SecondInputFragment ---
    private val _updateSecondInput = MutableLiveData<UiState<String>>()

    fun updateSecondInput(userType: String)  {
       updateMyUserTypeUseCase(viewModelScope, userType) {
           _updateSecondInput.postValue(it)
       }
    }

    // --- ThirdInputFragment ---
    private val _updateThirdInput = MutableLiveData<UiState<String>>()
    val updateThirdInput: LiveData<UiState<String>>
        get() = _updateThirdInput

    fun updateThirdInput(selected: List<String>) {
        updateMyUserSelectedListUseCase(viewModelScope, selected) {
            _updateThirdInput.postValue(it)
        }
    }

    // --- InputBottomSheet ---
    private val _checkUserInfo = MutableLiveData<UiState<UserInfoModel>>()
    val checkUserInfo: LiveData<UiState<UserInfoModel>>
        get() = _checkUserInfo

    fun checkInput()  {
        getMyUserInfoUseCase(viewModelScope) {
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