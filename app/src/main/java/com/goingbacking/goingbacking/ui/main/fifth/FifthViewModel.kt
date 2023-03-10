package com.goingbacking.goingbacking.ui.main.fifth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.UserInfoModel
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel
import com.example.domain.usecase.userInfo.GetUserInfoUseCase
import com.example.domain.usecase.userInfo.UpdateUserInfoUseCase
import com.example.domain.usecase.whatToDo.AddWhatToDoMonthUseCase
import com.example.domain.usecase.whatToDo.AddWhatToDoYearUseCase
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.repository.fifth.FifthRepositoryIF
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FifthViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val whatToDoMonthUseCase: AddWhatToDoMonthUseCase,
    private val whatToDoYearUseCase: AddWhatToDoYearUseCase,
    val fifthRepository: FifthRepositoryIF
) : ViewModel() {

    /*
        FifthMainFragment
     */

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    private val _userInfoDTO = MutableLiveData<UiState<UserInfoModel>>()
    val userInfoDTO : LiveData<UiState<UserInfoModel>>
        get() = _userInfoDTO

    fun getFifthUserInfo() {
        getUserInfoUseCase(viewModelScope) {
            _userInfoDTO.value = it
        }
    }


//    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
//    private val _userInfoDTOs = MutableLiveData<UiState<UserInfoDTO>>()
//    val userInfoDTO : LiveData<UiState<UserInfoDTO>>
//        get() = _userInfoDTOs
//
//    fun getFifthUserInfo() = viewModelScope.launch {
//        _userInfoDTOs.value = UiState.Loading
//        fifthRepository.getFifthUserInfo  { _userInfoDTOs.value = it }
//    }

    /*
     ChangeInfoActivity
     */

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    private val _updatedUserInfoDTO = MutableLiveData<UiState<String>>()

    fun updateFifthUserInfo(nickname :String, type :String, selected : List<String>) {
        updateUserInfoUseCase(viewModelScope, nickname, type, selected) {
            _updatedUserInfoDTO.value = it
        }
    }

    // 개인 정보 수정
//    private val _userInfo = MutableLiveData<UiState<String>>()
//
//    fun reviseUserInfo(nickname :String, type :String, selected : List<String>) = viewModelScope.launch {
//        _userInfo.value = UiState.Loading
//        fifthRepository.reviseUserInfo(nickname, type, selected) {
//            _userInfo.postValue(it)
//        }
//    }


    // 로그 아웃
    private val _signout = MutableLiveData<UiState<String>>()
    val signout: LiveData<UiState<String>>
        get() = _signout

    fun signout() = viewModelScope.launch {
        _signout.value = UiState.Loading
        fifthRepository.signout() {
            _signout.value = it
        }
    }



    // month whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitMonthDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoMonthTime(whatToDoMonthModel: WhatToDoMonthModel) = viewModelScope.launch {
        whatToDoMonthUseCase(viewModelScope, whatToDoMonthModel) {
            _whatToDoInitMonthDTOs.value = it
        }
    }

    // year whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitYearDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoYearTime(whatToDoYearModel: WhatToDoYearModel) = viewModelScope.launch {
        whatToDoYearUseCase(viewModelScope, whatToDoYearModel) {
            _whatToDoInitYearDTOs.value = it
        }
    }



}