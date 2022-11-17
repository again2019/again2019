package com.goingbacking.goingbacking.Repository.First

import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.firestore.FieldValue

interface FirstRepositoryIF {
    fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)

    fun updateTmpTimeDayInfo(wakeUpTime1: String, wakeUpTime2:String, count : FieldValue, result: (UiState<String>) -> Unit)
    fun updateTmpTimeMonthInfo(wakeUpTime1: String, wakeUpTime2: String, count :FieldValue, result: (UiState<String>) -> Unit)
    fun updateTmpTimeYearInfo(wakeUpTime: String, count :FieldValue, result: (UiState<String>) -> Unit)
    fun updateWhatToDoMonthInfo(yyyyMM:String, whatToDo: String, count: FieldValue, result: (UiState<String>) -> Unit)
    fun updateWhatToDoYearInfo(yyyy: String, whatToDo: String, count: FieldValue, result: (UiState<String>) -> Unit)

    fun updateRankMonthInfo(yyyyMM: String, count :FieldValue, result: (UiState<String>) -> Unit)
    fun updateRankYearInfo(yyyy: String, count :FieldValue, result: (UiState<String>) -> Unit)


    fun getWhatToDoInfo(result: (UiState<List<String>>) -> Unit)
}