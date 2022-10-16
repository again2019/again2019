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
    private val _addInitSaveTimeDay = MutableLiveData<UiState<String>>()
    val addInitSaveTimeDay: LiveData<UiState<String>>
        get() = _addInitSaveTimeDay

    private val _addInitSaveTimeMonth = MutableLiveData<UiState<String>>()
    val addInitSaveTimeMonth: LiveData<UiState<String>>
        get() = _addInitSaveTimeMonth

    private val _addInitSaveTimeYear = MutableLiveData<UiState<String>>()
    val addInitSaveTimeYear: LiveData<UiState<String>>
        get() = _addInitSaveTimeYear


    fun addInitSaveTimeDayInfo() {
        _addInitSaveTimeDay.value = UiState.Loading
        alarmRepository.addInitSaveTimeDayInfo() {
            _addInitSaveTimeDay.value = it
        }
    }

    fun addInitSaveTimeMonthInfo() {
        _addInitSaveTimeMonth.value = UiState.Loading
        alarmRepository.addInitSaveTimeMonthInfo() {
            _addInitSaveTimeMonth.value = it
        }
    }

    fun addInitSaveTimeYearInfo() {
        _addInitSaveTimeYear.value = UiState.Loading
        alarmRepository.addInitSaveTimeYearInfo() {
            _addInitSaveTimeYear.value = it
        }
    }
}