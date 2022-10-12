package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState
import java.time.LocalDate

interface MainRepositoryIF {
    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)
    fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit)
    fun addDateInfo(date: DateDTO, result: (UiState<String>) -> Unit)
    fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit)
    fun getThirdDateInfo2(result: (UiState<DateDTO>) -> Unit)
    fun getThirdCalendarInfo(yearList : MutableList<String>,result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)
    fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)

}