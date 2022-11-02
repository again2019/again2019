package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState

interface RankRepositoryIF {
    fun getSecondSaveDayInfo(result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit)
    fun getSecondSaveMonthInfo(result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit)
    fun getSecondSaveYearInfo(result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit)

    fun getSecondWhatToDoMonthInfo(result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit)
    fun getSecondWhatToDoYearInfo(result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)



}