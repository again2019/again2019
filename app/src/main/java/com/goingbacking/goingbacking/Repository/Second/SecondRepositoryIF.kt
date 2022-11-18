package com.goingbacking.goingbacking.Repository.Second

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState
import java.time.LocalDate

interface SecondRepositoryIF {
    /*
    SecondMainFragment1
     */

    // 매일 통계를 보여주는 코드
    fun getSecondSaveDayInfo(result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit)
    // 달 통계를 보여주는 코드
    fun getSecondSaveMonthInfo(result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit)
    // 연도 통계를 보여주는 코드
    fun getSecondSaveYearInfo(result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit)


    /*
    SecondMainFragment2
     */

    // 어떤 자기계발을 했는지 달 통계를 보여주는 코드
    fun getSecondWhatToDoMonthInfo(result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit)
    // 어떤 자기계발을 했는지 연도 통계를 보여주는 코드
    fun getSecondWhatToDoYearInfo(result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)

    fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit)
    fun getThirdDateInfo2(year_month: String, result: (UiState<DateDTO>) -> Unit)

    fun getThirdCalendarInfo(yearList : MutableList<String>,result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)

    // CalendarDetailBottomSheet
    fun getSelectedDateInfo(year_month: String, date: String, result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit)



}