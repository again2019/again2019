package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Repository.MainRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    val mainRepository: MainRepositoryIF) : ViewModel() {

    private val _userInfoDTOs = MutableLiveData<UiState<UserInfoDTO>>()
    val userInfoDTO : LiveData<UiState<UserInfoDTO>>
        get() = _userInfoDTOs

    private val _eventDTOs = MutableLiveData<UiState<String>>()
    val eventDTO : LiveData<UiState<String>>
        get() = _eventDTOs

    private val _dateDTOs = MutableLiveData<UiState<String>>()
    val dateDTOs : LiveData<UiState<String>>
        get() = _dateDTOs

    fun getFifthUserInfo()  {
        _userInfoDTOs.value = UiState.Loading
        mainRepository.getFifthUserInfo { _userInfoDTOs.value = it }
    }

    fun addScheduleEventInfo(path1 :String, path2: String, event: Event) {
        _eventDTOs.value = UiState.Loading
        mainRepository.addEventInfo(path1, path2, event) { _eventDTOs.value = it}
    }

    fun addDateInfo(date: DateDTO) {
        _dateDTOs.value = UiState.Loading
        mainRepository.addDateInfo(date) { _dateDTOs.value = it}
    }

}