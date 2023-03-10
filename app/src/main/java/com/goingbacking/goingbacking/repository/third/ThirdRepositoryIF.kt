package com.goingbacking.goingbacking.repository.third

import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.DateDTO
import com.goingbacking.goingbacking.model.Event
import java.time.LocalDate

interface ThirdRepositoryIF {

    /*
    ScheduleInputFragment2
     */

    // 날짜만 데이터 베이스에 저장
    fun addDateInfo(yearMonth : String, date: DateDTO, result: (UiState<String>) -> Unit)

    // 스케줄을 데이터 베이스에 저장
    fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit)

    /*
    ThirdMainFragment
     */

    // 스케줄이 있는 날짜를 불러오는 코드
    fun getThirdDateInfo1(year_month: String, result: (UiState<DateDTO>) -> Unit)

    fun getThirdDateInfo2(year_month: String, result: (UiState<List<String>>) -> Unit)

    // 해당 스케줄을 제거하는 코드
    fun deleteThirdCalendarInfo(eventDate :String, timeStamp : String, result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)

    // 닉네임 불러오는 코드
    fun getNickNameInfo(result: (UiState<String>) -> Unit)

    /*
    ThirdMainFragment
    TotalCalendarActivity
     */
    fun getSelectedDateInfo(year_month: String, date: String, result: (UiState<MutableList<Event>>) -> Unit)

    // 해당 달의 정보를 Map에 담는 코드
    fun getAllCalendarInfo(result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)
}