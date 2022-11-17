package com.goingbacking.goingbacking.Repository.Forth

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.UiState

interface RankRepositoryIF {


    // 상대방 통계에 대한 정보 불러오기
    fun getSecondSaveDayInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit)
    fun getSecondSaveMonthInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit)
    fun getSecondSaveYearInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit)

    fun getSecondWhatToDoMonthInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit)
    fun getSecondWhatToDoYearInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)

    // 상대방 좋아요, 응원 댓글 수에 대한 정보 불러오기
    fun getSaveTimeMonthInfo(destinationUid: String, result: (UiState<NewSaveTimeMonthDTO>) -> Unit)
    fun getSaveTimeYearInfo(destinationUid: String, result: (UiState<NewSaveTimeYearDTO>) -> Unit )

    // 상대방 개인 정보 불러오기
    fun getFifthUserInfo(destinationUid: String, result: (UiState<UserInfoDTO>) -> Unit)

    // 좋아요 버튼 기능 month, year
    fun likeButtonInfo(destinationUid :String, state :String, result: (UiState<String>) -> Unit)


}
