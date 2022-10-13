package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.util.UiState

interface TmpTimeRepositoryIF {
    fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)

    fun updateTmpTimeDayInfo(wakeUpTime: String, uidWakeUpTime: String, count :Double, result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)
    fun updateTmpTimeMonthInfo(wakeUpTime: String, uidWakeUpTime: String, count :Double, result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)
    fun updateTmpTimeYearInfo(wakeUpTime: String, count :Double, result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)


}