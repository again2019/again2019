package com.example.presentation.ui.main.forth


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.*
import com.example.domain.usecase.savedTime.other.GetOtherSavedTimeDayUseCase
import com.example.domain.usecase.savedTime.other.GetOtherSavedTimeMonthUseCase
import com.example.domain.usecase.userInfo.other.GetOtherUserInfoUseCase
import com.example.domain.usecase.userInfo.other.UpdateOtherLikeButtonUseCase
import com.example.domain.usecase.whatToDo.other.GetOtherWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.other.GetOtherWhatToDoYearUseCase
import com.example.domain.util.Response
import com.example.domain.util.UiState
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RankViewModel @Inject constructor(
    private val getOtherUserInfoUseCase: GetOtherUserInfoUseCase,
    private val getOtherSavedTimeDayUseCase: GetOtherSavedTimeDayUseCase,
    private val getOtherSavedTimeMonthUseCase: GetOtherSavedTimeMonthUseCase,
    private val getOtherWhatToDoMonthUseCase: GetOtherWhatToDoMonthUseCase,
    private val getOtherWhatToDoYearUseCase: GetOtherWhatToDoYearUseCase,
    private val updateLikeButtonUseCase: UpdateOtherLikeButtonUseCase,
):ViewModel() {

    /*
    RankActivity1
     */

    // 일별 통계 받아오는 코드
    private val _secondSaveDayDTOs = MutableLiveData<UiState<ArrayList<SavedTimeDayModel>>>()
    val secondSaveDayDTOs : LiveData<UiState<ArrayList<SavedTimeDayModel>>>
        get() = _secondSaveDayDTOs

    fun getSecondSaveDayInfo(destinationUid :String) {
        getOtherSavedTimeDayUseCase(viewModelScope, destinationUid) {
            _secondSaveDayDTOs.postValue(it)
        }
    }

    // 달별 자기계발 통계 받아오는 코드
    private val _secondwhatToDoMonthDTOs = MutableLiveData<UiState<ArrayList<WhatToDoMonthModel>>>()
    val secondwhatToDoMonthDTOs : LiveData<UiState<ArrayList<WhatToDoMonthModel>>>
        get() = _secondwhatToDoMonthDTOs

    fun getSecondWhatToDoMonthInfo(destinationUid :String) {
        getOtherWhatToDoMonthUseCase(viewModelScope, destinationUid) {
            _secondwhatToDoMonthDTOs.value = it
        }
    }


    /*
    RankActivity2
     */

    // 달별 통계 받아오는 코드
    private val _secondSaveMonthDTOs = MutableLiveData<UiState<ArrayList<SavedTimeMonthModel>>>()
    val secondSaveMonthDTOs : LiveData<UiState<ArrayList<SavedTimeMonthModel>>>
        get() = _secondSaveMonthDTOs

    fun getSecondSaveMonthInfo(destinationUid :String) {
        getOtherSavedTimeMonthUseCase(viewModelScope, destinationUid) {
            _secondSaveMonthDTOs.postValue(it)
        }
    }

    // 연도별 자기계발 통계 받아오는 코드
    private val _secondwhatToDoYearDTOs = MutableLiveData<UiState<ArrayList<WhatToDoYearModel>>>()
    val secondwhatToDoYearDTOs : LiveData<UiState<ArrayList<WhatToDoYearModel>>>
        get() = _secondwhatToDoYearDTOs

    fun getSecondWhatToDoYearInfo(destinationUid :String) {
        getOtherWhatToDoYearUseCase (viewModelScope, destinationUid) {
            _secondwhatToDoYearDTOs.value = it
        }
    }

    /*
    RankActivity1
    RankActivity2
     */


    // 개인정보 받아오는 코드
    private val _userInfoDTOs = MutableLiveData<UiState<UserInfoModel>>()
    val userInfoDTO : LiveData<UiState<UserInfoModel>>
        get() = _userInfoDTOs

    fun getFifthUserInfo(destinationUid: String) {
        getOtherUserInfoUseCase(viewModelScope, destinationUid) {
            _userInfoDTOs.postValue(it)
        }
    }

    // 좋아요 버튼 클릭/해제시 나타내는 코드
    private val _likeButtonInfo = MutableLiveData<Response<String>>()
    val likeButtonInfo : LiveData<Response<String>>
        get() = _likeButtonInfo

    fun likeButtonInfo(destinationUid :String, state :String) {
        viewModelScope.launch(Dispatchers.IO) {
            _likeButtonInfo.postValue(Response.Loading)
            updateLikeButtonUseCase(
                destinationUid,
                state,
                onResult = {
                    _likeButtonInfo.postValue(Response.Success(it))
                },
                onError = {
                    _likeButtonInfo.postValue(Response.Failure(it!!))
                }).collect {
                    
                }
//                response.onSuccess {
//                    _likeButtonInfo.postValue(Response.Success("Success"))
//                }.onError {
//                    _likeButtonInfo.postValue(Response.Failure(this.message()))
//                }.onException {
//                    _likeButtonInfo.postValue(Response.Except(this.exception.cause!!))
//                }

        }


    }


}