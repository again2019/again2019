package com.goingbacking.goingbacking.Repository.Third

import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.util.UiState

interface ThirdRepositoryIF {

    // 날짜만 데이터 베이스에 저장
    fun addDateInfo(yearMonth : String, date: DateDTO, result: (UiState<String>) -> Unit)

}