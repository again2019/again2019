package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.goingbacking.goingbacking.Repository.AlarmRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmViewModel @Inject constructor(
    val alarmRepository : AlarmRepositoryIF
) : ViewModel() {

    private val _addFirstInitSaveTimeMonth = MutableLiveData<UiState<String>>()

    // 매달마다 데이터 베이스를 초기화하는 코드
    fun addFirstInitSaveTimeMonthInfo () = viewModelScope.launch {
        _addFirstInitSaveTimeMonth.value = UiState.Loading
        alarmRepository.addFirstInitSaveTimeMonthInfo {
            _addFirstInitSaveTimeMonth.value = it
        }
    }


    private val _addFirstInitSaveTimeYear = MutableLiveData<UiState<String>>()

    // 매년마다 데이터 베이스를 초기화하는 코드
    fun addFirstInitSaveTimeYearInfo () = viewModelScope.launch {
    _addFirstInitSaveTimeYear.value = UiState.Loading
        alarmRepository.addFirstInitSaveTimeYearInfo {
            _addFirstInitSaveTimeYear.value = it
        }
    }

    private val _addInitSaveTimeDay = MutableLiveData<UiState<String>>()

    // 매일마다 데이터 베이스를 초기화하는 코드
    fun addInitSaveTimeDayInfo() = viewModelScope.launch  {
        _addInitSaveTimeDay.value = UiState.Loading
        alarmRepository.addInitSaveTimeDayInfo {
            _addInitSaveTimeDay.value = it
        }
    }







}