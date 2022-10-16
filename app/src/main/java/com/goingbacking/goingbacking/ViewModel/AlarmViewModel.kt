package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Repository.AlarmRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    val alarmRepository : AlarmRepositoryIF
) : ViewModel() {
    private val _addFirstInitSaveTimeMonth = MutableLiveData<UiState<String>>()
    val firstInitSaveTimeMonth: LiveData<UiState<String>>
        get() = _addFirstInitSaveTimeMonth

    private val _addFirstInitSaveTimeYear = MutableLiveData<UiState<String>>()
    val firstInitSaveTimeYear: LiveData<UiState<String>>
        get() = _addFirstInitSaveTimeYear

    private val _addInitSaveTimeDay = MutableLiveData<UiState<String>>()
    val initSaveTimeDay: LiveData<UiState<String>>
        get() = _addInitSaveTimeDay



    fun addFirstInitSaveTimeMonthInfo () {
        _addFirstInitSaveTimeMonth.value = UiState.Loading
        alarmRepository.addFirstInitSaveTimeMonthInfo {
            _addFirstInitSaveTimeMonth.value = it
        }
    }
    fun addFirstInitSaveTimeYearInfo () {
        _addFirstInitSaveTimeYear.value = UiState.Loading
        alarmRepository.addFirstInitSaveTimeYearInfo {
            _addFirstInitSaveTimeYear.value = it
        }
    }

    fun addInitSaveTimeDayInfo() {
        _addInitSaveTimeDay.value = UiState.Loading
        alarmRepository.addInitSaveTimeDayInfo {
            _addInitSaveTimeDay.value = it
        }
    }





}