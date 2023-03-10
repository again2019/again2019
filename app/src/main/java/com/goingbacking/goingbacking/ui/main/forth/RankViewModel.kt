package com.goingbacking.goingbacking.ui.main.forth


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.*
import com.goingbacking.goingbacking.repository.forth.RankRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RankViewModel @Inject constructor(
    val rankRepository : RankRepositoryIF
):ViewModel() {

    /*
    RankActivity1
     */

    // 일별 통계 받아오는 코드
    private val _secondSaveDayDTOs = MutableLiveData<UiState<ArrayList<SaveTimeDayDTO>>>()
    val secondSaveDayDTOs : LiveData<UiState<ArrayList<SaveTimeDayDTO>>>
        get() = _secondSaveDayDTOs

    fun getSecondSaveDayInfo(destinationUid :String) {
        _secondSaveDayDTOs.value = UiState.Loading
        rankRepository.getSecondSaveDayInfo(destinationUid) { _secondSaveDayDTOs.value = it }
    }

    // 달별 자기계발 통계 받아오는 코드
    private val _secondwhatToDoMonthDTOs = MutableLiveData<UiState<ArrayList<WhatToDoMonthDTO>>>()
    val secondwhatToDoMonthDTOs : LiveData<UiState<ArrayList<WhatToDoMonthDTO>>>
        get() = _secondwhatToDoMonthDTOs

    fun getSecondWhatToDoMonthInfo(destinationUid :String) {
        _secondwhatToDoMonthDTOs.value = UiState.Loading
        rankRepository.getSecondWhatToDoMonthInfo(destinationUid) { _secondwhatToDoMonthDTOs.value = it }
    }


    /*
    RankActivity2
     */

    // 달별 통계 받아오는 코드
    private val _secondSaveMonthDTOs = MutableLiveData<UiState<ArrayList<SaveTimeMonthDTO>>>()
    val secondSaveMonthDTOs : LiveData<UiState<ArrayList<SaveTimeMonthDTO>>>
        get() = _secondSaveMonthDTOs

    fun getSecondSaveMonthInfo(destinationUid :String) {
        _secondSaveMonthDTOs.value = UiState.Loading
        rankRepository.getSecondSaveMonthInfo(destinationUid) { _secondSaveMonthDTOs.value = it }
    }

    // 연도별 자기계발 통계 받아오는 코드
    private val _secondwhatToDoYearDTOs = MutableLiveData<UiState<ArrayList<WhatToDoYearDTO>>>()
    val secondwhatToDoYearDTOs : LiveData<UiState<ArrayList<WhatToDoYearDTO>>>
        get() = _secondwhatToDoYearDTOs

    fun getSecondWhatToDoYearInfo(destinationUid :String) {
        _secondwhatToDoYearDTOs.value = UiState.Loading
        rankRepository.getSecondWhatToDoYearInfo(destinationUid) { _secondwhatToDoYearDTOs.value = it }
    }

    /*
    RankActivity1
    RankActivity2
     */


    // 개인정보 받아오는 코드
    private val _userInfoDTOs = MutableLiveData<UiState<UserInfoDTO>>()
    val userInfoDTO : LiveData<UiState<UserInfoDTO>>
        get() = _userInfoDTOs

    fun getFifthUserInfo(destinationUid: String) = viewModelScope.launch {
        _userInfoDTOs.value = UiState.Loading
        rankRepository.getFifthUserInfo(destinationUid)  { _userInfoDTOs.value = it }
    }

    // 좋아요 버튼 클릭/해제시 나타내는 코드
    private val _likeButtonInfo = MutableLiveData<UiState<String>>()
    val likeButtonInfo : LiveData<UiState<String>>
        get() = _likeButtonInfo

    fun likeButtonInfo(destinationUid :String, state :String) = viewModelScope.launch {
        _likeButtonInfo.value = UiState.Loading
        rankRepository.likeButtonInfo(destinationUid, state) { _likeButtonInfo.postValue(it) }
    }


}