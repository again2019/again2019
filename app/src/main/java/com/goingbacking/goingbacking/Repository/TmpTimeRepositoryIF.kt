package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.firestore.FieldValue

interface TmpTimeRepositoryIF {
    fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)

    fun updateTmpTimeDayInfo(wakeUpTime1: String, wakeUpTime2:String, count : FieldValue, result: (UiState<String>) -> Unit)
    fun updateTmpTimeMonthInfo(wakeUpTime1: String, wakeUpTime2: String, count :FieldValue, result: (UiState<String>) -> Unit)
    fun updateTmpTimeYearInfo(wakeUpTime: String, count :FieldValue, result: (UiState<String>) -> Unit)
    fun updateWhatToDoMonthInfo(whatToDo: String, count: FieldValue, result: (UiState<String>) -> Unit)
    fun updateWhatToDoYearInfo(whatToDo: String, count: FieldValue, result: (UiState<String>) -> Unit)

}