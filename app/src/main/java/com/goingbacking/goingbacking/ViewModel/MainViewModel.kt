package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Repository.MainRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
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

    private val _thirdDateDTOs = MutableLiveData<UiState<DateDTO>>()
    val thirdDateDTOs : LiveData<UiState<DateDTO>>
        get() = _thirdDateDTOs

    private val _thirdCalendarDTOs = MutableLiveData<UiState<MutableMap<LocalDate, List<Event>>>>()
    val thirdCalendarDTOs : LiveData<UiState<MutableMap<LocalDate, List<Event>>>>
        get() = _thirdCalendarDTOs

    private val _tmpTimeDTOs = MutableLiveData<UiState<ArrayList<TmpTimeDTO>>>()
    val tmpTimeDTOs : LiveData<UiState<ArrayList<TmpTimeDTO>>>
        get() = _tmpTimeDTOs

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

    fun getThirdDateInfo() {
        _thirdDateDTOs.value = UiState.Loading
        mainRepository.getThirdDateInfo { _thirdDateDTOs.value = it }
    }

    fun getThirdCalendarInfo(yearList : MutableList<String>) {
        _thirdCalendarDTOs.value = UiState.Loading
        mainRepository.getThirdCalendarInfo(yearList) { _thirdCalendarDTOs.value = it }
    }

    fun getTmpTimeInfo() {
        _tmpTimeDTOs.value = UiState.Loading
        mainRepository.getTmpTimeInfo { _tmpTimeDTOs.value = it }
    }
}