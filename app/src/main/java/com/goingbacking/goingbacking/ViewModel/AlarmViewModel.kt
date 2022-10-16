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

    private val _addInitSaveTimeMonth = MutableLiveData<UiState<String>>()
    val initSaveTimeMonth: LiveData<UiState<String>>
        get() = _addInitSaveTimeMonth

    private val _addInitSaveTimeYear = MutableLiveData<UiState<String>>()
    val initSaveTimeYear: LiveData<UiState<String>>
        get() = _addInitSaveTimeYear

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

    fun addInitSaveTimeMonthInfo() {
        _addInitSaveTimeMonth.value = UiState.Loading
        alarmRepository.addInitSaveTimeMonthInfo {
            _addInitSaveTimeMonth.value = it
        }
    }

    fun addInitSaveTimeYearInfo() {
        _addInitSaveTimeYear.value = UiState.Loading
        alarmRepository.addInitSaveTimeYearInfo {
            _addInitSaveTimeYear.value = it
        }
    }




}