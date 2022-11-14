package com.goingbacking.goingbacking.Repository.Third

import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.util.UiState

interface ThirdRepositoryIF {

    // 날짜만 데이터 베이스에 저장
    fun addDateInfo(yearMonth : String, date: DateDTO, result: (UiState<String>) -> Unit)

    // 스케줄을 데이터 베이스에 저장
    fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit)

}