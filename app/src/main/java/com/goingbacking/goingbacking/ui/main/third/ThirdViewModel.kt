package com.goingbacking.goingbacking.ui.main.third

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.DateModel
import com.example.domain.model.ScheduleModel
import com.example.domain.usecase.scheduleAndDate.*
import com.example.domain.usecase.userInfo.my.GetMyNickNameUseCase
import com.example.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class ThirdViewModel @Inject constructor(
    private val getMyNickNameUseCase: GetMyNickNameUseCase,
    private val addDateUseCase: AddDateUseCase,
    private val addScheduleUseCase: AddScheduleUseCase,
    private val deleteSchedulesUseCase: DeleteSchedulesUseCase,
    private val getAllSchedulesUseCase: GetAllSchedulesUseCase,
    private val getDateUseCase: GetDateUseCase,
    private val getDateListUseCase: GetDateListUseCase,
    private val getSelectedSchedulesUseCase: GetSelectedSchedulesUseCase,
) : ViewModel() {

    /*
    ScheduleInputFragment2
     */

    // 날짜만 데이터 베이스에 저장
    private val _dateDTOs = MutableLiveData<UiState<String>>()

    fun addDateInfo(yearMonth: String, date: DateModel) {
        addDateUseCase(viewModelScope, yearMonth, date) {
            _dateDTOs.value = it
        }
    }

    // 스케줄을 데이터 베이스에 저장
    private val _eventDTOs = MutableLiveData<UiState<String>>()

    fun addScheduleEventInfo(path1 :String, path2: String, schedule: ScheduleModel) {
        addScheduleUseCase(viewModelScope, path1, path2, schedule) {
            _eventDTOs.value = it
        }
    }

    /*
    ThirdMainFragment
     */

    // 날짜의 스케줄 유무를 가져오는 코드
    private val _thirdDateDTOs1 = MutableLiveData<UiState<DateModel>>()
    val thirdDateDTOs1 : LiveData<UiState<DateModel>>
        get() = _thirdDateDTOs1

    fun getThirdDateInfo1(yearMonth: String) {
        getDateUseCase(viewModelScope, yearMonth) {
            _thirdDateDTOs1.postValue(it)
        }
    }

    // 날짜의 스케줄 유무를 가져오는 코드
    private val _thirdDateDTOs2 = MutableLiveData<UiState<List<String>>>()
    val thirdDateDTOs2 : LiveData<UiState<List<String>>>
        get() = _thirdDateDTOs2

    fun getThirdDateInfo2(yearMonth: String) {
        getDateListUseCase(viewModelScope, yearMonth) {
            _thirdDateDTOs2.postValue(it)
        }
    }

    // 날짜의 스케줄 삭제하는 코드
    private val _deleteThirdCalendarDTOs = MutableLiveData<UiState<MutableMap<LocalDate, List<ScheduleModel>>>>()
    val deleteThirdCalendarDTOs : LiveData<UiState<MutableMap<LocalDate, List<ScheduleModel>>>>
        get() = _deleteThirdCalendarDTOs



    fun deleteThirdCalendarInfo(scheduleDate: String, timeStamp: String) {
        deleteSchedulesUseCase(viewModelScope, scheduleDate, timeStamp) {
            _deleteThirdCalendarDTOs.postValue(it)
        }
    }


    // 닉네임을 가져오는 코드
    private val _nickNameInfoDTOs = MutableLiveData<UiState<String>>()
    val nickNameInfoDTOs : LiveData<UiState<String>>
        get() = _nickNameInfoDTOs

    fun getNickNameInfo()  {
        getMyNickNameUseCase(viewModelScope) {
            _nickNameInfoDTOs.postValue(it)
        }

    }

    /*
    ThirdMainFragment
    TotalCalendarActivity
     */

    // 날짜의 선택된 스케줄을 가져오는 코드
    private val _thirdSelectedDateDTOs = MutableLiveData<UiState<List<ScheduleModel>>>()
    val thirdSelectedDateDTOs : LiveData<UiState<List<ScheduleModel>>>
        get() = _thirdSelectedDateDTOs

    fun getSelectedDateInfo(yearMonth: String, date: String) {
        getSelectedSchedulesUseCase(viewModelScope, yearMonth, date) {
            _thirdSelectedDateDTOs.postValue(it)
        }
    }

    private val _thirdAllCalendarDTOs = MutableLiveData<UiState<MutableMap<LocalDate, List<ScheduleModel>>>>()
    val thirdAllCalendarDTOs : LiveData<UiState<MutableMap<LocalDate, List<ScheduleModel>>>>
        get() = _thirdAllCalendarDTOs

    fun getAllCalendarInfo() {
        getAllSchedulesUseCase(viewModelScope) {
            _thirdAllCalendarDTOs.postValue(it)
        }
    }

}