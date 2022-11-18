package com.goingbacking.goingbacking.Repository.Forth

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState

interface RankRepositoryIF {
    /*
    RankActivity1
     */

    // 일별 통계 받아오는 코드
    fun getSecondSaveDayInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit)
    // 달별 자기계발 통계 받아오는 코드
    fun getSecondWhatToDoMonthInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit)

    /*
     RankActivity2
      */

    // 달별 통계 받아오는 코드
    fun getSecondSaveMonthInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit)
    // 연도별 자기계발 통계 받아오는 코드
    fun getSecondWhatToDoYearInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)

    /*
    RankActivity1
    RankActivity2
     */


    // 개인정보 받아오는 코드
    fun getFifthUserInfo(destinationUid: String, result: (UiState<UserInfoDTO>) -> Unit)

    // 좋아요 버튼 기능 month, year
    fun likeButtonInfo(destinationUid :String, state :String, result: (UiState<String>) -> Unit)


}
