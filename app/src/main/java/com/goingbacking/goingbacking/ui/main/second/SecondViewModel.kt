package com.goingbacking.goingbacking.ui.main.second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel
import com.example.domain.usecase.whatToDo.GetMyWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.GetMyWhatToDoYearUseCase
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.*
import com.goingbacking.goingbacking.repository.second.SecondRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor (
    private val getMyWhatToDoMonthUseCase: GetMyWhatToDoMonthUseCase,
    private val getMyWhatToDoYearUseCase: GetMyWhatToDoYearUseCase,
    val secondRepository: SecondRepositoryIF
) : ViewModel() {


    /*
    SecondMainFragment1
     */

    // 매일 통계를 보여주는 코드
    private val _secondSaveDayDTOs = MutableLiveData<UiState<ArrayList<SaveTimeDayDTO>>>()
    val secondSaveDayDTOs : LiveData<UiState<ArrayList<SaveTimeDayDTO>>>
        get() = _secondSaveDayDTOs

    fun getSecondSaveDayInfo() {
        _secondSaveDayDTOs.value = UiState.Loading
        secondRepository.getSecondSaveDayInfo() { _secondSaveDayDTOs.value = it }
    }

    // 달 통계를 보여주는 코드
    private val _secondSaveMonthDTOs = MutableLiveData<UiState<ArrayList<SaveTimeMonthDTO>>>()
    val secondSaveMonthDTOs : LiveData<UiState<ArrayList<SaveTimeMonthDTO>>>
        get() = _secondSaveMonthDTOs

    fun getSecondSaveMonthInfo() {
        _secondSaveYearDTOs.value = UiState.Loading
        secondRepository.getSecondSaveMonthInfo() { _secondSaveMonthDTOs.value = it }
    }

    // 연도 통계를 보여주는 코드
    private val _secondSaveYearDTOs = MutableLiveData<UiState<ArrayList<SaveTimeYearDTO>>>()
    val secondSaveYearDTOs : LiveData<UiState<ArrayList<SaveTimeYearDTO>>>
        get() = _secondSaveYearDTOs

    fun getSecondSaveYearInfo() {
        _secondSaveYearDTOs.value = UiState.Loading
        secondRepository.getSecondSaveYearInfo() { _secondSaveYearDTOs.value = it }
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