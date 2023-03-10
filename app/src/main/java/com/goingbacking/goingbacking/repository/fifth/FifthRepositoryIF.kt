package com.goingbacking.goingbacking.repository.fifth

import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.*

interface FifthRepositoryIF {

    /*
     FifthMainFragment
     */

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)
    // 회원 탈퇴
    fun logout(result: (UiState<String>) -> Unit) // 삭제할 코드
    fun signout(result: (UiState<String>) -> Unit)


    /*
     ChangeInfoActivity
     */

    // 개인 정보 수정
    fun reviseUserInfo(nickname :String, type :String, selected : List<String>, result: (UiState<String>) -> Unit)

    // month whattodo chart를 위해 초기화하는 코드
    fun addInitWhatToDoMonthTime(whatToDoMonthDTO : WhatToDoMonthDTO, result: (UiState<String>) -> Unit)
    // year whattodo chart를 위해 초기화하는 코드
    fun addInitWhatToDoYearTime(whatToDoYearDTO : WhatToDoYearDTO, result: (UiState<String>) -> Unit)

}