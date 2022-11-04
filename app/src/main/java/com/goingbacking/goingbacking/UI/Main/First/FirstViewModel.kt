package com.goingbacking.goingbacking.UI.Main.First

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Repository.First.FirstRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    val firstRepository: FirstRepositoryIF
) : ViewModel(){

    private val _tmpTimeDTOs = MutableLiveData<UiState<ArrayList<TmpTimeDTO>>>()
    val tmpTimeDTOs : LiveData<UiState<ArrayList<TmpTimeDTO>>>
        get() = _tmpTimeDTOs

    private val _tmpTimeDayDTOs = MutableLiveData<UiState<String>>()
    val tmpTimeDayDTOs : LiveData<UiState<String>>
        get() = _tmpTimeDayDTOs

    private val _tmpTimeMonthDTOs = MutableLiveData<UiState<String>>()
    val tmpTimeMonthDTOs : LiveData<UiState<String>>
        get() = _tmpTimeMonthDTOs

    private val _tmpTimeYearDTOs = MutableLiveData<UiState<String>>()
    val tmpTimeYearDTOs : LiveData<UiState<String>>
        get() = _tmpTimeYearDTOs

    private val _whatToDoMonthDTOs = MutableLiveData<UiState<String>>()
    val whatToDoMonthDTOs : LiveData<UiState<String>>
        get() = _whatToDoMonthDTOs

    private val _whatToDoYearDTOs = MutableLiveData<UiState<String>>()
    val whatToDoYearDTOs : LiveData<UiState<String>>
        get() = _whatToDoYearDTOs

    private val _whatToDoListDTOs = MutableLiveData<UiState<String>>()
    val whatToDoListDTOs : LiveData<UiState<String>>
        get() = _whatToDoListDTOs

    fun getTmpTimeInfo() {
        _tmpTimeDTOs.value = UiState.Loading
        firstRepository.getTmpTimeInfo { _tmpTimeDTOs.value = it }

    }

    fun updateTmpTimeDayInfo(wakeUpTime1: String,
                             wakeUpTime2: String,
                             count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeDayInfo(wakeUpTime1, wakeUpTime2, count) { _tmpTimeDayDTOs.value = it }
    }

    fun updateTmpTimeMonthInfo(wakeUpTime1: String,
                             wakeUpTime2: String,
                             count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeMonthInfo(wakeUpTime1, wakeUpTime2, count) { _tmpTimeMonthDTOs.value = it }
    }

    fun updateTmpTimeYearInfo(wakeUpTime: String,
                               count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeYearInfo(wakeUpTime, count) { _tmpTimeYearDTOs.value = it }
    }

    fun updateWhatToDoMonthInfo(whatToDo: String, count: FieldValue) {
        _whatToDoMonthDTOs.value = UiState.Loading
        firstRepository.updateWhatToDoMonthInfo(whatToDo, count) { _whatToDoMonthDTOs.value = it }
    }

    fun updateWhatToDoYearInfo(whatToDo: String, count: FieldValue) {
        _whatToDoYearDTOs.value = UiState.Loading
        firstRepository.updateWhatToDoYearInfo(whatToDo, count) { _whatToDoYearDTOs.value = it }
    }

    fun getWhatToDoInfo() {
        _whatToDoListDTOs.value = UiState.Loading
        firstRepository.getWhatToDoInfo { _whatToDoListDTOs.value = it }

    }




    }