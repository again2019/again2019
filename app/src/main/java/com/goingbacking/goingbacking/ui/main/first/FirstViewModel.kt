package com.goingbacking.goingbacking.ui.main.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.TmpTimeModel
import com.example.domain.usecase.GetTmpTimeUseCase
import com.example.domain.usecase.whatToDo.UpdateWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.UpdateWhatToDoYearUseCase
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.TmpTimeDTO
import com.goingbacking.goingbacking.model.UserInfoDTO
import com.goingbacking.goingbacking.repository.first.FirstRepositoryIF
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val updateWhatToDoMonthUseCase: UpdateWhatToDoMonthUseCase,
    private val updateWhatToDoYearUseCase: UpdateWhatToDoYearUseCase,
    private val getTmpTimeUseCase: GetTmpTimeUseCase,
    val firstRepository: FirstRepositoryIF
) : ViewModel(){

    /*
    TmpTimeActivity
     */
    private val _tmpTimeRepository = MutableLiveData<ArrayList<TmpTimeModel>>()
    val tmpTimeRepository: LiveData<ArrayList<TmpTimeModel>> = _tmpTimeRepository

    fun getTmpTimeInfo2() {
        getTmpTimeUseCase(viewModelScope) {
            _tmpTimeRepository.value = it
        }
    }
    // 임시 저장된 정보를 가져오는 코드
    private val _tmpTimeDTOs = MutableLiveData<UiState<ArrayList<TmpTimeDTO>>>()
    val tmpTimeDTOs : LiveData<UiState<ArrayList<TmpTimeDTO>>>
        get() = _tmpTimeDTOs

    fun getTmpTimeInfo() {
        _tmpTimeDTOs.value = UiState.Loading
        firstRepository.getTmpTimeInfo { _tmpTimeDTOs.value = it }
    }

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    private val _userInfoDTOs = MutableLiveData<UiState<UserInfoDTO>>()
    val userInfoDTO : LiveData<UiState<UserInfoDTO>>
        get() = _userInfoDTOs

    fun getFifthUserInfo() = viewModelScope.launch {
        _userInfoDTOs.value = UiState.Loading
        firstRepository.getFifthUserInfo  { _userInfoDTOs.value = it }
    }

    /*
    WhatToDoSaveBottomSheet
     */

    // 임시 저장된 정보 -> 최종 정보로 바꾸고 삭제하는 코드
    private val _deletetmpTimeDTOs = MutableLiveData<UiState<String>>()

    fun deleteTmpTimeInfo(startTime: String) {
        _deletetmpTimeDTOs.value = UiState.Loading
        firstRepository.deleteTmpTimeInfo(startTime) { _deletetmpTimeDTOs.value = it }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Day)
    private val _tmpTimeDayDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeDayInfo(wakeUpTime1: String,
                             wakeUpTime2: String,
                             count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeDayInfo(wakeUpTime1, wakeUpTime2, count) { _tmpTimeDayDTOs.value = it }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Month)
    private val _tmpTimeMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeMonthInfo(wakeUpTime1: String,
                               wakeUpTime2: String,
                               count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeMonthInfo(wakeUpTime1, wakeUpTime2, count) { _tmpTimeMonthDTOs.value = it }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Year)
    private val _tmpTimeYearDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeYearInfo(wakeUpTime: String,
                              count: FieldValue
    ) {
        _tmpTimeDayDTOs.value = UiState.Loading
        firstRepository.updateTmpTimeYearInfo(wakeUpTime, count) { _tmpTimeYearDTOs.value = it }
    }

    // 임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Month)
    private val _rankMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateRankMonthInfo(yyyyMM: String,
                            count: FieldValue,
    ) {
        _rankMonthDTOs.value = UiState.Loading
        firstRepository.updateRankMonthInfo(yyyyMM, count) { _rankMonthDTOs.value = it }
    }

    //임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Year)
    private val _rankYearDTOs = MutableLiveData<UiState<String>>()

    fun updateRankYearInfo(yyyy: String,
                           count: FieldValue
    ) {
        _rankYearDTOs.value = UiState.Loading
        firstRepository.updateRankYearInfo(yyyy, count) { _rankYearDTOs.value = it }
    }


    //임시 저장된 정보 -> 최종 정보로 어떤 자기계발을 할 것인지로 바꾸는 코드 (Month)
    private val _whatToDoMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateWhatToDoMonthInfo(yyyyMM : String, whatToDo: String, count: Double) {
        updateWhatToDoMonthUseCase(viewModelScope, yyyyMM, whatToDo, count) {
            _whatToDoMonthDTOs.value = it
        }
    }

    //임시 저장된 정보 -> 최종 정보로 어떤 자기계발을 할 것인지로 바꾸는 코드 (Year)
    private val _whatToDoYearDTOs = MutableLiveData<UiState<String>>()

    fun updateWhatToDoYearInfo(yyyy : String, whatToDo: String, count: Double) {
        updateWhatToDoYearUseCase(viewModelScope, yyyy, whatToDo, count) {
            _whatToDoYearDTOs.value = it
        }
    }

    // 원하는 자기계발을 불러오느 코드
    private val _whatToDoListDTOs = MutableLiveData<UiState<List<String>>>()
    val whatToDoListDTOs : LiveData<UiState<List<String>>>
        get() = _whatToDoListDTOs

    fun getWhatToDoInfo() {
        _whatToDoListDTOs.value = UiState.Loading
        firstRepository.getWhatToDoInfo { _whatToDoListDTOs.value = it }

    }






}