package com.goingbacking.goingbacking.UI.Main.Second

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.Repository.Second.SecondRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor (
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
    private val _secondwhatToDoMonthDTOs = MutableLiveData<UiState<ArrayList<WhatToDoMonthDTO>>>()
    val secondwhatToDoMonthDTOs : LiveData<UiState<ArrayList<WhatToDoMonthDTO>>>
        get() = _secondwhatToDoMonthDTOs

    fun getSecondWhatToDoMonthInfo() {
        _secondwhatToDoMonthDTOs.value = UiState.Loading
        secondRepository.getSecondWhatToDoMonthInfo() { _secondwhatToDoMonthDTOs.value = it }
    }

    // 어떤 자기계발을 했는지 연도 통계를 보여주는 코드
    private val _secondwhatToDoYearDTOs = MutableLiveData<UiState<ArrayList<WhatToDoYearDTO>>>()
    val secondwhatToDoYearDTOs : LiveData<UiState<ArrayList<WhatToDoYearDTO>>>
        get() = _secondwhatToDoYearDTOs

    fun getSecondWhatToDoYearInfo() {
        _secondwhatToDoYearDTOs.value = UiState.Loading
        secondRepository.getSecondWhatToDoYearInfo() { _secondwhatToDoYearDTOs.value = it }
    }
}