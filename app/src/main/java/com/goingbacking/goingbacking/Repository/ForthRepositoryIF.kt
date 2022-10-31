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

    // 좋아요 버튼 기능
    fun likeButtonInfo()

}