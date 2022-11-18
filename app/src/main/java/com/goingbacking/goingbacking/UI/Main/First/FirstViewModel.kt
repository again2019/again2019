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

    /*
    TmpTimeActivity
     */

    // 임시 저장된 정보를 가져오는 코드
    private val _tmpTimeDTOs = MutableLiveData<UiState<ArrayList<TmpTimeDTO>>>()
    val tmpTimeDTOs : LiveData<UiState<ArrayList<TmpTimeDTO>>>
        get() = _tmpTimeDTOs

    fun getTmpTimeInfo() {
        _tmpTimeDTOs.value = UiState.Loading
        firstRepository.getTmpTimeInfo { _tmpTimeDTOs.value = it }
    }


    // 임시 저장된 정보 -> 최종 정보로 바꾸고 삭제하는 코드
    private val _deletetmpTimeDTOs = MutableLiveData<UiState<String>>()

    fun deleteTmpTimeInfo(startTime: String) {
        _deletetmpTimeDTOs.value = UiState.Loading
        firstRepository.deleteTmpTimeInfo(startTime) { _deletetmpTimeDTOs.value = it }
    }


    private val _tmpTimeDayDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeDayInfo(wakeUpTime1: String,
                             wakeUpTime2: String,
                             count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeDayInfo(wakeUpTime1, wakeUpTime2, count) { _tmpTimeDayDTOs.value = it }
    }


    private val _tmpTimeMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeMonthInfo(wakeUpTime1: String,
                               wakeUpTime2: String,
                               count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeMonthInfo(wakeUpTime1, wakeUpTime2, count) { _tmpTimeMonthDTOs.value = it }
    }


    private val _tmpTimeYearDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeYearInfo(wakeUpTime: String,
                              count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeYearInfo(wakeUpTime, count) { _tmpTimeYearDTOs.value = it }
    }


    private val _whatToDoMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateWhatToDoMonthInfo(yyyyMM : String, whatToDo: String, count: FieldValue) {
        _whatToDoMonthDTOs.value = UiState.Loading
        firstRepository.updateWhatToDoMonthInfo(yyyyMM, whatToDo, count) { _whatToDoMonthDTOs.value = it }
    }



    private val _whatToDoYearDTOs = MutableLiveData<UiState<String>>()


    fun updateWhatToDoYearInfo(yyyy : String, whatToDo: String, count: FieldValue) {
        _whatToDoYearDTOs.value = UiState.Loading
        firstRepository.updateWhatToDoYearInfo(yyyy, whatToDo, count) { _whatToDoYearDTOs.value = it }
    }

    private val _whatToDoListDTOs = MutableLiveData<UiState<List<String>>>()
    val whatToDoListDTOs : LiveData<UiState<List<String>>>
        get() = _whatToDoListDTOs

    fun getWhatToDoInfo() {
        _whatToDoListDTOs.value = UiState.Loading
        firstRepository.getWhatToDoInfo { _whatToDoListDTOs.value = it }

    }


    private val _rankMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateRankMonthInfo(yyyyMM: String,
                            count: FieldValue,
    ) {
        _rankMonthDTOs.value = UiState.Loading
        firstRepository.updateRankMonthInfo(yyyyMM, count) { _rankMonthDTOs.value = it }
    }

    private val _rankYearDTOs = MutableLiveData<UiState<String>>()

    fun updateRankYearInfo(yyyy: String,
                              count: FieldValue
    ) {
        _rankYearDTOs.value = UiState.Loading
        firstRepository.updateRankYearInfo(yyyy, count) { _rankYearDTOs.value = it }
    }





}