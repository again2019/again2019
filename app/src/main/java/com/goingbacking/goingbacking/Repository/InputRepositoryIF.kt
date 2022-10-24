package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState

interface InputRepositoryIF {

    // 닉네임 정보 입력
    fun addFirstInput(userInfoDTO : UserInfoDTO, result: (UiState<String>) -> Unit)

    // 사용자 타입 입력
    fun updateSecondInput(userType: String, result: (UiState<String>) -> Unit)

    // 통근 시간에 할 일 입력
    fun updateThirdInput(whatToDo: String, result: (UiState<String>) -> Unit)

    fun checkInput(result: (UiState<UserInfoDTO>) -> Unit)
}