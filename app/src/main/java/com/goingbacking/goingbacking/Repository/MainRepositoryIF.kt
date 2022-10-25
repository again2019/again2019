package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState
import java.time.LocalDate

interface MainRepositoryIF {

    // FifthMainFragment
    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)

    fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit)
    fun addDateInfo(date: DateDTO, result: (UiState<String>) -> Unit)

    // ThirdMainFragment
    fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit)
    fun getThirdDateInfo2(year_month: String, result: (UiState<DateDTO>) -> Unit)

    fun getThirdCalendarInfo(yearList : MutableList<String>,result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)

    // SecondMainFragment
    // SecondMainFragment1
    fun getSecondSaveDayInfo(result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit)
    fun getSecondSaveMonthInfo(result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit)
    fun getSecondSaveYearInfo(result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit)
    // SecondMainFragment2
    fun getSecondWhatToDoMonthInfo(result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit)
    fun getSecondWhatToDoYearInfo(result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)

}