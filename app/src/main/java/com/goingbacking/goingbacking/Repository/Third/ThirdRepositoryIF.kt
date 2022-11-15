package com.goingbacking.goingbacking.Repository.Third

import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.util.UiState
import java.time.LocalDate

interface ThirdRepositoryIF {

    // 날짜만 데이터 베이스에 저장
    fun addDateInfo(yearMonth : String, date: DateDTO, result: (UiState<String>) -> Unit)

    // 스케줄을 데이터 베이스에 저장
    fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit)

    // 스케줄을 불러오는 코드
    fun getThirdCalendarInfo(yearList : MutableList<String>,result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)

    // 스케줄이 있는 날짜를 불러오는 코드
    fun getThirdDateInfo(year_month: String, result: (UiState<DateDTO>) -> Unit)

    // CalendarDetailBottomSheet
    fun getSelectedDateInfo(year_month: String, date: String, result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)

}