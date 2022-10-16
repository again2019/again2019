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

    fun addInitSaveTimeDayInfo() {
        _addInitSaveTimeDay.value = UiState.Loading
        alarmRepository.addInitSaveTimeDayInfo() {
            _addInitSaveTimeDay.value = it
        }

    }
}