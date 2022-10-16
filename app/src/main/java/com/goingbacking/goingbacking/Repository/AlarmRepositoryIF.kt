package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.SaveTimeDayDTO
import com.goingbacking.goingbacking.Model.SaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.SaveTimeYearDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState

interface AlarmRepositoryIF {

    // 맨 처음 로그인 시 day 초기화
    // 맨 처음 로그인 시 month 초기화
    // 맨 처음 로그인 시 year 초기화


    
    // day마다 초기화
    fun addInitSaveTimeDayInfo(result: (UiState<String>) -> Unit)
    // month마다 초기화
    fun addInitSaveTimeMonthInfo(result: (UiState<String>) -> Unit)
    // year마다 초기화
    fun addInitSaveTimeYearInfo(result: (UiState<String>) -> Unit)


}