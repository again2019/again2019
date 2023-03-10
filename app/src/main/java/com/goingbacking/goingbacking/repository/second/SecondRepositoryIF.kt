package com.goingbacking.goingbacking.repository.second

import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.*

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


}