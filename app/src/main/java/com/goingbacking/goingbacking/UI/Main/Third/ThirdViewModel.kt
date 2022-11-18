package com.goingbacking.goingbacking.UI.Main.Third

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.Repository.Third.ThirdRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class ThirdViewModel @Inject constructor(
    val thirdRepository: ThirdRepositoryIF
) : ViewModel() {


    private val _dateDTOs = MutableLiveData<UiState<String>>()

    fun addDateInfo(yearMonth: String, date: DateDTO) {
        _dateDTOs.value = UiState.Loading
        thirdRepository.addDateInfo(yearMonth, date) { _dateDTOs.value = it}
    }

    private val _eventDTOs = MutableLiveData<UiState<String>>()

    fun addScheduleEventInfo(path1 :String, path2: String, event: Event) {
        _eventDTOs.value = UiState.Loading
        thirdRepository.addEventInfo(path1, path2, event) { _eventDTOs.value = it}
    }

    private val _thirdDateDTOs = MutableLiveData<UiState<DateDTO>>()
    val thirdDateDTOs : LiveData<UiState<DateDTO>>
        get() = _thirdDateDTOs

    fun getThirdDateInfo(year_month:String) = viewModelScope.launch {
        _thirdDateDTOs.value = UiState.Loading
        thirdRepository.getThirdDateInfo(year_month) { _thirdDateDTOs.value = it }
    }

    private val _thirdCalendarDTOs = MutableLiveData<UiState<MutableMap<LocalDate, List<Event>>>>()
    val thirdCalendarDTOs : LiveData<UiState<MutableMap<LocalDate, List<Event>>>>
        get() = _thirdCalendarDTOs

    fun getThirdCalendarInfo(yearList : MutableList<String>) {
        _thirdCalendarDTOs.value = UiState.Loading
        thirdRepository.getThirdCalendarInfo(yearList) { _thirdCalendarDTOs.value = it }
    }

    private val _deleteThirdCalendarDTOs = MutableLiveData<UiState<MutableMap<LocalDate, List<Event>>>>()
    val deleteThirdCalendarDTOs : LiveData<UiState<MutableMap<LocalDate, List<Event>>>>
        get() = _deleteThirdCalendarDTOs

    fun deleteThirdCalendarInfo(eventDate: String, timeStamp: String) {
        _deleteThirdCalendarDTOs.value = UiState.Loading
        thirdRepository.deleteThirdCalendarInfo(eventDate, timeStamp) { _deleteThirdCalendarDTOs.value = it }
    }


    private val _thirdSelectedDateDTOs = MutableLiveData<UiState<MutableList<Event>>>()
    val thirdSelectedDateDTOs : LiveData<UiState<MutableList<Event>>>
        get() = _thirdSelectedDateDTOs

    fun getSelectedDateInfo(year_month: String, date: String) {
        _thirdSelectedDateDTOs.value = UiState.Loading
        thirdRepository.getSelectedDateInfo(year_month, date) { _thirdSelectedDateDTOs.value = it }
    }

    private val _nickNameInfoDTOs = MutableLiveData<UiState<String>>()
    val nickNameInfoDTOs : LiveData<UiState<String>>
        get() = _nickNameInfoDTOs

    fun getNickNameInfo() = viewModelScope.launch {
        _nickNameInfoDTOs.value = UiState.Loading
        thirdRepository.getNickNameInfo  { _nickNameInfoDTOs.value = it }
    }


}