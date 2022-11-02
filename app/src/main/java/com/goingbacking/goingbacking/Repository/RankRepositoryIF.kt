package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState

interface RankRepositoryIF {
    fun getSecondSaveDayInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit)
    fun getSecondSaveMonthInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit)
    fun getSecondSaveYearInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit)

    fun getSecondWhatToDoMonthInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit)
    fun getSecondWhatToDoYearInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)



}