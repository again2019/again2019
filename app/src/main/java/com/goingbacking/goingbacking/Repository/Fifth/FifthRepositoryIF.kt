package com.goingbacking.goingbacking.Repository.Fifth

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState

interface FifthRepositoryIF {
    // 개인 정보 불러오는 코드
    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)
    // 회원 탈퇴
    fun logout(result: (UiState<String>) -> Unit)
    fun signout(result: (UiState<String>) -> Unit)
    // 정보 수정 저장
    fun addFirstInput(userInfoDTO: UserInfoDTO, result: (UiState<String>) -> Unit)

    // month whattodo chart를 위해 초기화하는 코드
    fun addInitWhatToDoMonthTime(whatToDoMonthDTO : WhatToDoMonthDTO, result: (UiState<String>) -> Unit)
    // year whattodo chart를 위해 초기화하는 코드
    fun addInitWhatToDoYearTime(whatToDoYearDTO : WhatToDoYearDTO, result: (UiState<String>) -> Unit)

}