package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState

interface MainRepositoryIF {
    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)
    fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit)
    fun addDateInfo(date: DateDTO, result: (UiState<String>) -> Unit)
    fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit)
    fun getThirdDateInfo2(result: (UiState<DateDTO>) -> Unit)

}