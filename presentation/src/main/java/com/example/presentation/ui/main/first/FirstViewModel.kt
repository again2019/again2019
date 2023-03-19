package com.example.presentation.ui.main.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.TmpTimeModel
import com.example.domain.model.UserInfoModel
import com.example.domain.usecase.savedTime.common.UpdateSavedTimeAboutMonthRankUseCase
import com.example.domain.usecase.savedTime.common.UpdateSavedTimeAboutYearRankUseCase
import com.example.domain.usecase.myTmpTime.*
import com.example.domain.usecase.userInfo.my.GetMyUserInfoUseCase
import com.example.domain.usecase.whatToDo.my.UpdateWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.my.UpdateWhatToDoYearUseCase
import com.example.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val getMyUserInfoUseCase: GetMyUserInfoUseCase,
    private val updateWhatToDoMonthUseCase: UpdateWhatToDoMonthUseCase,
    private val updateWhatToDoYearUseCase: UpdateWhatToDoYearUseCase,
    private val getTmpTimeUseCase: GetTmpTimeUseCase,
    private val deleteTmpTimeUseCase: DeleteTmpTimeUseCase,
    private val updateTmpTimeDayUseCase: UpdateTmpTimeDayUseCase,
    private val updateTmpTimeMonthUseCase: UpdateTmpTimeMonthUseCase,
    private val updateTmpTimeYearUseCase: UpdateTmpTimeYearUseCase,
    private val updateSavedTimeAboutMonthRankUseCase: UpdateSavedTimeAboutMonthRankUseCase,
    private val updateSavedTimeAboutYearRankUseCase: UpdateSavedTimeAboutYearRankUseCase,
) : ViewModel(){

    /*
    TmpTimeActivity
     */

    // 수정 예정본
    private val _tmpTimeModelList = MutableLiveData<UiState<ArrayList<TmpTimeModel>>>()
    val tmpTimeModelList: LiveData<UiState<ArrayList<TmpTimeModel>>>
        get() = _tmpTimeModelList
    fun getTmpTimeModelList() = viewModelScope.launch {
        getTmpTimeUseCase(viewModelScope) {
            _tmpTimeModelList.postValue(it)
        }
    }


    // 임시 저장된 정보를 가져오는 코드
    private val _tmpTimeDTOs = MutableLiveData<UiState<ArrayList<TmpTimeModel>>>()
    val tmpTimeDTOs: LiveData<UiState<ArrayList<TmpTimeModel>>>
        get() = _tmpTimeDTOs

    fun getTmpTimeInfo() {
        getTmpTimeUseCase(viewModelScope) {
            _tmpTimeDTOs.value = it
        }
    }

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    private val _userInfoDTOs = MutableLiveData<UiState<UserInfoModel>>()
    val userInfoDTO : LiveData<UiState<UserInfoModel>>
        get() = _userInfoDTOs

    fun getFifthUserInfo() {
        getMyUserInfoUseCase(viewModelScope) {
            _userInfoDTOs.value= it
        }

    }

    /*
    WhatToDoSaveBottomSheet
     */

    // 임시 저장된 정보 -> 최종 정보로 바꾸고 삭제하는 코드
    private val _deletetmpTimeDTOs = MutableLiveData<UiState<String>>()

    fun deleteTmpTimeInfo(startTime: String) {
        deleteTmpTimeUseCase(viewModelScope, startTime) {
            _deletetmpTimeDTOs.value = it
        }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Day)
    private val _tmpTimeDayDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeDayInfo(wakeUpTime1: String,
                             wakeUpTime2: String,
                             count: Double
    ) {
        updateTmpTimeDayUseCase(viewModelScope, wakeUpTime1, wakeUpTime2, count) {
            _tmpTimeDayDTOs.value = it
        }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Month)
    private val _tmpTimeMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeMonthInfo(wakeUpTime1: String,
                               wakeUpTime2: String,
                               count: Double
    ) {
        updateTmpTimeMonthUseCase(viewModelScope, wakeUpTime1, wakeUpTime2, count) {
            _tmpTimeMonthDTOs.value = it
        }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Year)
    private val _tmpTimeYearDTOs = MutableLiveData<UiState<String>>()

    fun updateTmpTimeYearInfo(wakeUpTime: String,
                              count: Double
    ) {
        updateTmpTimeYearUseCase(viewModelScope, wakeUpTime, count) {
            _tmpTimeYearDTOs.value = it
        }
    }

    // 임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Month)
    private val _rankMonthDTOs = MutableLiveData<UiState<String>>()

    fun updateRankMonthInfo(yyyyMM: String,
                            count: Double,
    ) {
        updateSavedTimeAboutMonthRankUseCase(viewModelScope, yyyyMM, count) {
            _rankMonthDTOs.value = it
        }
    }

    //임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Year)
    private val _rankYearDTOs = MutableLiveData<UiState<String>>()

    fun updateRankYearInfo(yyyy: String,
                           count: Double
    ) {
        updateSavedTimeAboutYearRankUseCase(viewModelScope, yyyy, count) {
            _rankYearDTOs.value = it
        }
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
}