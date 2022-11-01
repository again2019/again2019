package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.NewSaveTimeYearDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.Repository.ForthRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForthViewModel @Inject constructor(
    val forthRepository: ForthRepositoryIF
) : ViewModel() {

    // year 기준으로 랭킹화
    private val _newSaveTimeYear = MutableLiveData<UiState<ArrayList<NewSaveTimeYearDTO>>>()
    val newSaveTimeYear : LiveData<UiState<ArrayList<NewSaveTimeYearDTO>>>
        get() = _newSaveTimeYear

    fun getSaveTimeYearInfo() = viewModelScope.launch {
        _newSaveTimeYear.value = UiState.Loading
        forthRepository.getSaveTimeYearInfo { _newSaveTimeYear.value = it }
    }

    // month 기준으로 랭킹화
    private val _newSaveTimeMonth = MutableLiveData<UiState<ArrayList<NewSaveTimeMonthDTO>>>()
    val newSaveTimeMonth : LiveData<UiState<ArrayList<NewSaveTimeMonthDTO>>>
        get() = _newSaveTimeMonth

    fun getSaveTimeMonthInfo() = viewModelScope.launch {
        _newSaveTimeMonth.value = UiState.Loading
        forthRepository.getSaveTimeMonthInfo { _newSaveTimeMonth.value = it }
    }
    

}