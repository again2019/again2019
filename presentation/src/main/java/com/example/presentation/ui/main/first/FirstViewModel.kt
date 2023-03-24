package com.example.presentation.ui.main.first

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.util.DatabaseResult
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
import kotlinx.coroutines.Dispatchers
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


    // 임시 저장된 정보를 가져오는 코드
    private val _getTmpTimeModelList = MutableLiveData<UiState<List<TmpTimeModel>>>()
    val getTmpTimeModelList: LiveData<UiState<List<TmpTimeModel>>>
        get() = _getTmpTimeModelList
    fun getTmpTimeModelList() {
        viewModelScope.launch(Dispatchers.IO) {
            _getTmpTimeModelList.postValue(UiState.Loading)
            getTmpTimeUseCase() { result ->
                // Result -> UiState Mapper
                when(result) {
                    is DatabaseResult.Success -> {
                        _getTmpTimeModelList.postValue(UiState.Success(result.data))
                    }
                    is DatabaseResult.Failure -> {
                        _getTmpTimeModelList.postValue(UiState.Failure(result.message))
                    }
                }
            }
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
    private val _deleteTmpTimeModel = MutableLiveData<UiState<String>>()
    val deleteTmpTimeModel : LiveData<UiState<String>>
        get() = _deleteTmpTimeModel

    fun deleteTmpTimeModel(
        startTime: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTmpTimeUseCase(
                startTime
            ) { result ->
                when(result) {
                    is DatabaseResult.Success -> {
                        _deleteTmpTimeModel.postValue(UiState.Success(result.data))
                    }
                    is DatabaseResult.Failure -> {
                        _deleteTmpTimeModel.postValue(UiState.Failure(result.message))
                    }
                }

            }
        }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Day)
    private val _updateTmpTimeDayModel = MutableLiveData<UiState<String>>()
    val updateTmpTimeDayModel : LiveData<UiState<String>>
        get() = _updateTmpTimeDayModel

    fun updateTmpTimeDayInfo(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTmpTimeDayUseCase(
                wakeUpTime1,
                wakeUpTime2,
                count
            ) { result ->
                when(result) {
                    is DatabaseResult.Success -> {
                        _updateTmpTimeDayModel.postValue(UiState.Success(result.data))
                    }
                    is DatabaseResult.Failure -> {
                        _updateTmpTimeDayModel.postValue(UiState.Failure(result.message))
                    }
                }
            }
        }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Month)
    private val _updateTmpTimeMonthModel = MutableLiveData<UiState<String>>()
    val updateTmpTimeMonthModel : LiveData<UiState<String>>
        get() = _updateTmpTimeMonthModel

    fun updateTmpTimeMonthInfo(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTmpTimeMonthUseCase(
                wakeUpTime1,
                wakeUpTime2,
                count
            ) { result ->
                when(result) {
                    is DatabaseResult.Success -> {
                        _updateTmpTimeMonthModel.postValue(UiState.Success(result.data))
                    }
                    is DatabaseResult.Failure -> {
                        _updateTmpTimeMonthModel.postValue(UiState.Failure(result.message))
                    }
                }
            }
        }
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Year)
    private val _updateTmpTimeYearModel = MutableLiveData<UiState<String>>()
    val updateTmpTimeYearModel : LiveData<UiState<String>>
        get() = _updateTmpTimeYearModel

    fun updateTmpTimeYearInfo(
        wakeUpTime: String,
        count: Double
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTmpTimeYearUseCase(
                wakeUpTime,
                count
            ) { result ->
                when(result) {
                    is DatabaseResult.Success -> {
                        _updateTmpTimeYearModel.postValue(UiState.Success(result.data))
                    }
                    is DatabaseResult.Failure -> {
                        _updateTmpTimeYearModel.postValue(UiState.Failure(result.message))
                    }
                }
            }
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