package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.NewSaveTimeYearDTO
import com.goingbacking.goingbacking.Model.SaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.SaveTimeYearDTO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.UserInfo

interface ForthRepositoryIF {

    fun getSaveTimeMonthInfo(result: (UiState<ArrayList<NewSaveTimeMonthDTO>>) -> Unit)
    fun getSaveTimeYearInfo(result: (UiState<ArrayList<NewSaveTimeYearDTO>>) -> Unit )

    // 좋아요 버튼 기능 month, year
    fun likeButtonInfo(destinationUid :String, state :String)

    // 응원 댓글 받아오는 코드
    fun getCheerInfo(destinationUid :String, result: (UiState<List<String>>) -> Unit)
    // 응원 댓글 입력
    fun addCheerInfo(destinationUid: String, nickname: String, text:String, result: (UiState<String>) -> Unit)
    // 응원 댓글 삭제
    fun deleteCheerInfo(destinationUid: String, text:String, result: (UiState<String>) -> Unit)


}