package com.goingbacking.goingbacking.repository.first

import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.TmpTimeDTO
import com.goingbacking.goingbacking.model.UserInfoDTO
import com.google.firebase.firestore.FieldValue

interface FirstRepositoryIF {

    /*
    TmpTimeActivity
     */

    // 임시 저장된 정보를 가져오는 코드
    fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit)

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)

    /*
    WhatToDoSaveBottomSheet
     */

    // 임시 저장된 정보 -> 최종 정보로 바꾸고 삭제하는 코드
    fun deleteTmpTimeInfo(startTime : String, result: (UiState<String>) -> Unit)

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Day)
    fun updateTmpTimeDayInfo(wakeUpTime1: String, wakeUpTime2:String, count : FieldValue, result: (UiState<String>) -> Unit)
    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Month)
    fun updateTmpTimeMonthInfo(wakeUpTime1: String, wakeUpTime2: String, count :FieldValue, result: (UiState<String>) -> Unit)
    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Year)
    fun updateTmpTimeYearInfo(wakeUpTime: String, count :FieldValue, result: (UiState<String>) -> Unit)

    // 임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Month)
    fun updateRankMonthInfo(yyyyMM: String, count :FieldValue, result: (UiState<String>) -> Unit)
    //임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Year)
    fun updateRankYearInfo(yyyy: String, count :FieldValue, result: (UiState<String>) -> Unit)

    //임시 저장된 정보 -> 최종 정보로 어떤 자기계발을 할 것인지로 바꾸는 코드 (Month)
    fun updateWhatToDoMonthInfo(yyyyMM:String, whatToDo: String, count: FieldValue, result: (UiState<String>) -> Unit)
    //임시 저장된 정보 -> 최종 정보로 어떤 자기계발을 할 것인지로 바꾸는 코드 (Year)
    fun updateWhatToDoYearInfo(yyyy: String, whatToDo: String, count: FieldValue, result: (UiState<String>) -> Unit)

    // 원하는 자기계발을 불러오느 코드
}