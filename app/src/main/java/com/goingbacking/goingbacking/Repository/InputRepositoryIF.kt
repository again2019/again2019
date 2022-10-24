package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.util.UiState

interface InputRepositoryIF {

    // 닉네임 정보 입력
    fun addFirstInput(userInfoDTO : UserInfoDTO, result: (UiState<String>) -> Unit)

    // 사용자 타입 입력
    fun updateSecondInput(userType: String, result: (UiState<String>) -> Unit)

    // 통근 시간에 할 일 입력
    fun updateThirdInput(whatToDo: String, result: (UiState<String>) -> Unit)

    // bottom sheet에서 input 정보를 check하는데 정보들을 불러옴
    fun checkInput(result: (UiState<UserInfoDTO>) -> Unit)

    // whatToDo 정보들에 대한 초기화 정보들을 입력
    fun addInitWhatToDoMonthTime(whatToDoMonthDTO : WhatToDoMonthDTO, result: (UiState<String>) -> Unit)
    fun addInitWhatToDoYearTime(whatToDoYearDTO : WhatToDoYearDTO,result: (UiState<String>) -> Unit)
}