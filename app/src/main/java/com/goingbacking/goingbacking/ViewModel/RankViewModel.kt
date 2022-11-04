package com.goingbacking.goingbacking.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.Repository.RankRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RankViewModel @Inject constructor(
    val rankRepository :RankRepositoryIF):ViewModel() {

    private val _secondSaveYearDTOs = MutableLiveData<UiState<ArrayList<SaveTimeYearDTO>>>()
    val secondSaveYearDTOs : LiveData<UiState<ArrayList<SaveTimeYearDTO>>>
        get() = _secondSaveYearDTOs

    fun getSecondSaveYearInfo(destinationUid :String) {
        _secondSaveYearDTOs.value = UiState.Loading
        rankRepository.getSecondSaveYearInfo(destinationUid)  { _secondSaveYearDTOs.value = it }
    }

    private val _secondSaveMonthDTOs = MutableLiveData<UiState<ArrayList<SaveTimeMonthDTO>>>()
    val secondSaveMonthDTOs : LiveData<UiState<ArrayList<SaveTimeMonthDTO>>>
        get() = _secondSaveMonthDTOs

    fun getSecondSaveMonthInfo(destinationUid :String) {
        _secondSaveYearDTOs.value = UiState.Loading
        rankRepository.getSecondSaveMonthInfo(destinationUid) { _secondSaveMonthDTOs.value = it }
    }

    private val _secondSaveDayDTOs = MutableLiveData<UiState<ArrayList<SaveTimeDayDTO>>>()
    val secondSaveDayDTOs : LiveData<UiState<ArrayList<SaveTimeDayDTO>>>
        get() = _secondSaveDayDTOs

    fun getSecondSaveDayInfo(destinationUid :String) {
        _secondSaveDayDTOs.value = UiState.Loading
        rankRepository.getSecondSaveDayInfo(destinationUid) { _secondSaveDayDTOs.value = it }
    }

    private val _secondwhatToDoMonthDTOs = MutableLiveData<UiState<ArrayList<WhatToDoMonthDTO>>>()
    val secondwhatToDoMonthDTOs : LiveData<UiState<ArrayList<WhatToDoMonthDTO>>>
        get() = _secondwhatToDoMonthDTOs

    fun getSecondWhatToDoMonthInfo(destinationUid :String) {
        _secondwhatToDoMonthDTOs.value = UiState.Loading
        rankRepository.getSecondWhatToDoMonthInfo(destinationUid) { _secondwhatToDoMonthDTOs.value = it }
    }


    private val _secondwhatToDoYearDTOs = MutableLiveData<UiState<ArrayList<WhatToDoYearDTO>>>()
    val secondwhatToDoYearDTOs : LiveData<UiState<ArrayList<WhatToDoYearDTO>>>
        get() = _secondwhatToDoYearDTOs

    fun getSecondWhatToDoYearInfo(destinationUid :String) {
        _secondwhatToDoYearDTOs.value = UiState.Loading
        rankRepository.getSecondWhatToDoYearInfo(destinationUid) { _secondwhatToDoYearDTOs.value = it }
    }



}