package com.goingbacking.goingbacking.Repository.Fifth

import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState

interface FifthRepositoryIF {
    // 개인 정보 불러오는 코드
    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)
    // 회원 탈퇴
    fun logout(result: (UiState<String>) -> Unit)
    fun signout(result: (UiState<String>) -> Unit)
}