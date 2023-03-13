package com.goingbacking.goingbacking.ui.main.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.*
import com.example.domain.usecase.savedTime.my.GetMySavedTimeDayUseCase
import com.example.domain.usecase.savedTime.my.GetMySavedTimeMonthUseCase
import com.example.domain.usecase.savedTime.my.GetMySavedTimeYearUseCase
import com.example.domain.usecase.whatToDo.my.GetMyWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.my.GetMyWhatToDoYearUseCase
import com.example.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor (
    private val getMyWhatToDoMonthUseCase: GetMyWhatToDoMonthUseCase,
    private val getMyWhatToDoYearUseCase: GetMyWhatToDoYearUseCase,
    private val getMySavedTimeDayUseCase: GetMySavedTimeDayUseCase,
    private val getMySavedTimeMonthUseCase: GetMySavedTimeMonthUseCase,
    private val getMySavedTimeYearUseCase: GetMySavedTimeYearUseCase,
) : ViewModel() {


    /*
    SecondMainFragment1
     */

    // 매일 통계를 보여주는 코드
    private val _secondSaveDayDTOs = MutableLiveData<UiState<ArrayList<SavedTimeDayModel>>>()
    val secondSaveDayDTOs : LiveData<UiState<ArrayList<SavedTimeDayModel>>>
        get() = _secondSaveDayDTOs

    fun getSecondSaveDayInfo() {
        getMySavedTimeDayUseCase(viewModelScope) {
            _secondSaveDayDTOs.value = it
        }
    }

    // 달 통계를 보여주는 코드
    private val _secondSaveMonthDTOs = MutableLiveData<UiState<ArrayList<SavedTimeMonthModel>>>()
    val secondSaveMonthDTOs : LiveData<UiState<ArrayList<SavedTimeMonthModel>>>
        get() = _secondSaveMonthDTOs

    fun getSecondSaveMonthInfo() {
        getMySavedTimeMonthUseCase(viewModelScope) {
            _secondSaveMonthDTOs.value = it
        }
    }

    // 연도 통계를 보여주는 코드
    private val _secondSaveYearDTOs = MutableLiveData<UiState<ArrayList<SavedTimeYearModel>>>()
    val secondSaveYearDTOs : LiveData<UiState<ArrayList<SavedTimeYearModel>>>
        get() = _secondSaveYearDTOs

    fun getSecondSaveYearInfo() {
        getMySavedTimeYearUseCase(viewModelScope) {
            _secondSaveYearDTOs.value = it
        }
    }

    /*
    SecondMainFragment2
     */

    // 어떤 자기계발을 했는지 달 통계를 보여주는 코드
    private val _secondwhatToDoMonthDTOs = MutableLiveData<UiState<ArrayList<WhatToDoMonthModel>>>()
    val secondwhatToDoMonthDTOs : LiveData<UiState<ArrayList<WhatToDoMonthModel>>>
        get() = _secondwhatToDoMonthDTOs

    fun getSecondWhatToDoMonthInfo() {
        getMyWhatToDoMonthUseCase(viewModelScope) {
            _secondwhatToDoMonthDTOs.value = it
        }
    }

    // 어떤 자기계발을 했는지 연도 통계를 보여주는 코드
    private val _secondwhatToDoYearDTOs = MutableLiveData<UiState<ArrayList<WhatToDoYearModel>>>()
    val secondwhatToDoYearDTOs : LiveData<UiState<ArrayList<WhatToDoYearModel>>>
        get() = _secondwhatToDoYearDTOs

    fun getSecondWhatToDoYearInfo() {
        getMyWhatToDoYearUseCase(viewModelScope) {
            _secondwhatToDoYearDTOs.value = it
        }
    }
}