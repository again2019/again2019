package com.goingbacking.goingbacking.UI.Main.Fifth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FifthViewModel @Inject constructor(
    val fifthRepository: FifthRepositoryIF
) : ViewModel() {

    // fifthMainFragment

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    private val _userInfoDTOs = MutableLiveData<UiState<UserInfoDTO>>()
    val userInfoDTO : LiveData<UiState<UserInfoDTO>>
        get() = _userInfoDTOs

    fun getFifthUserInfo() = viewModelScope.launch {
        _userInfoDTOs.value = UiState.Loading
        fifthRepository.getFifthUserInfo  { _userInfoDTOs.value = it }
    }

    // 로그 아웃
    private val _logout = MutableLiveData<UiState<String>>()
    val logout: LiveData<UiState<String>>
        get() = _logout

    fun logout() = viewModelScope.launch {
        _logout.value = UiState.Loading
        fifthRepository.logout() {
            _logout.value = it
        }
    }







    // 개인 정보 수정
    private val _addUserInfo = MutableLiveData<UiState<String>>()

    fun addFirstInput(userInfoDTO: UserInfoDTO) = viewModelScope.launch {
        _addUserInfo.value = UiState.Loading
        fifthRepository.addFirstInput(userInfoDTO) {
            _addUserInfo.postValue(it)
        }
    }

    // month whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitMonthDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoMonthTime(whatToDoMonthDTO: WhatToDoMonthDTO) = viewModelScope.launch {
        _whatToDoInitMonthDTOs.value = UiState.Loading
        fifthRepository.addInitWhatToDoMonthTime(whatToDoMonthDTO) {
            _whatToDoInitMonthDTOs.postValue(it)
        }
    }

    // year whattodo chart를 위해 초기화하는 코드
    private val _whatToDoInitYearDTOs = MutableLiveData<UiState<String>>()

    fun addInitWhatToDoYearTime(whatToDoYearDTO: WhatToDoYearDTO) = viewModelScope.launch {
        _whatToDoInitYearDTOs.value = UiState.Loading
        fifthRepository.addInitWhatToDoYearTime(whatToDoYearDTO) {
            _whatToDoInitYearDTOs.postValue(it)
        }
    }



}