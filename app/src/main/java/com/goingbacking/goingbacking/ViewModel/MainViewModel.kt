package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.Repository.MainRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    val mainRepository: MainRepositoryIF) : ViewModel() {



    private val _eventDTOs = MutableLiveData<UiState<String>>()
    val eventDTO : LiveData<UiState<String>>
        get() = _eventDTOs

    private val _dateDTOs = MutableLiveData<UiState<String>>()
    val dateDTOs : LiveData<UiState<String>>
        get() = _dateDTOs

    private val _thirdDateDTOs = MutableLiveData<UiState<DateDTO>>()
    val thirdDateDTOs : LiveData<UiState<DateDTO>>
        get() = _thirdDateDTOs

    private val _thirdDateDTOs2 = MutableLiveData<UiState<DateDTO>>()
    val thirdDateDTOs2 : LiveData<UiState<DateDTO>>
        get() = _thirdDateDTOs2

    private val _thirdCalendarDTOs = MutableLiveData<UiState<MutableMap<LocalDate, List<Event>>>>()
    val thirdCalendarDTOs : LiveData<UiState<MutableMap<LocalDate, List<Event>>>>
        get() = _thirdCalendarDTOs

    private val _secondSaveYearDTOs = MutableLiveData<UiState<ArrayList<SaveTimeYearDTO>>>()
    val secondSaveYearDTOs : LiveData<UiState<ArrayList<SaveTimeYearDTO>>>
        get() = _secondSaveYearDTOs

    private val _secondSaveMonthDTOs = MutableLiveData<UiState<ArrayList<SaveTimeMonthDTO>>>()
    val secondSaveMonthDTOs : LiveData<UiState<ArrayList<SaveTimeMonthDTO>>>
        get() = _secondSaveMonthDTOs

    private val _secondSaveDayDTOs = MutableLiveData<UiState<ArrayList<SaveTimeDayDTO>>>()
    val secondSaveDayDTOs : LiveData<UiState<ArrayList<SaveTimeDayDTO>>>
        get() = _secondSaveDayDTOs

    private val _secondwhatToDoMonthDTOs = MutableLiveData<UiState<ArrayList<WhatToDoMonthDTO>>>()
    val secondwhatToDoMonthDTOs : LiveData<UiState<ArrayList<WhatToDoMonthDTO>>>
        get() = _secondwhatToDoMonthDTOs

    private val _secondwhatToDoYearDTOs = MutableLiveData<UiState<ArrayList<WhatToDoYearDTO>>>()
    val secondwhatToDoYearDTOs : LiveData<UiState<ArrayList<WhatToDoYearDTO>>>
        get() = _secondwhatToDoYearDTOs




    private val _thirdSelectedDateDTOs = MutableLiveData<UiState<MutableMap<LocalDate, List<Event>>>>()
    val thirdSelectedDateDTOs : LiveData<UiState<MutableMap<LocalDate, List<Event>>>>
        get() = _thirdSelectedDateDTOs


    fun addScheduleEventInfo(path1 :String, path2: String, event: Event) {
        _eventDTOs.value = UiState.Loading
        mainRepository.addEventInfo(path1, path2, event) { _eventDTOs.value = it}
    }

    fun addDateInfo(yearMonth: String, date: DateDTO) {
        _dateDTOs.value = UiState.Loading
        mainRepository.addDateInfo(yearMonth, date) { _dateDTOs.value = it}
    }

    fun getThirdDateInfo() {
        _thirdDateDTOs.value = UiState.Loading
        mainRepository.getThirdDateInfo { _thirdDateDTOs.postValue(it) }
    }
    fun getThirdDateInfo2(year_month:String) = viewModelScope.launch {
        _thirdDateDTOs2.value = UiState.Loading
        mainRepository.getThirdDateInfo2(year_month) { _thirdDateDTOs2.value = it }
    }

    fun getThirdCalendarInfo(yearList : MutableList<String>) {
        _thirdCalendarDTOs.value = UiState.Loading
        mainRepository.getThirdCalendarInfo(yearList) { _thirdCalendarDTOs.value = it }
    }


    fun getSelectedDateInfo(year_month: String, date: String) {
        _thirdSelectedDateDTOs.value = UiState.Loading
        mainRepository.getSelectedDateInfo(year_month, date) { _thirdSelectedDateDTOs.value = it }
    }



    fun getSecondSaveYearInfo() {
        _secondSaveYearDTOs.value = UiState.Loading
        mainRepository.getSecondSaveYearInfo() { _secondSaveYearDTOs.value = it }
    }

    fun getSecondSaveMonthInfo() {
        _secondSaveYearDTOs.value = UiState.Loading
        mainRepository.getSecondSaveMonthInfo() { _secondSaveMonthDTOs.value = it }
    }

    fun getSecondSaveDayInfo() {
        _secondSaveDayDTOs.value = UiState.Loading
        mainRepository.getSecondSaveDayInfo() { _secondSaveDayDTOs.value = it }
    }

    fun getSecondWhatToDoMonthInfo() {
        _secondwhatToDoMonthDTOs.value = UiState.Loading
        mainRepository.getSecondWhatToDoMonthInfo() { _secondwhatToDoMonthDTOs.value = it }
    }

    fun getSecondWhatToDoYearInfo() {
        _secondwhatToDoYearDTOs.value = UiState.Loading
        mainRepository.getSecondWhatToDoYearInfo() { _secondwhatToDoYearDTOs.value = it }
    }

}