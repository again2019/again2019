package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.SaveTimeDayDTO
import com.goingbacking.goingbacking.Model.SaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.SaveTimeYearDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState

interface AlarmRepositoryIF {
    fun addInitSaveTimeDayInfo(result: (UiState<String>) -> Unit)
    fun addInitSaveTimeMonthInfo(result: (UiState<String>) -> Unit)
    fun addInitSaveTimeYearInfo(result: (UiState<String>) -> Unit)
}