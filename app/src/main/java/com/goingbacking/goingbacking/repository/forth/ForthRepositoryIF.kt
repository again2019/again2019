package com.goingbacking.goingbacking.repository.forth

import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.model.NewSaveTimeYearDTO

interface ForthRepositoryIF {

    /*
    ForthMainFragment1
     */

    fun getSaveTimeMonthInfo(result: (UiState<ArrayList<NewSaveTimeMonthDTO>>) -> Unit)

    /*
    ForthMainFragment2
    */

    fun getSaveTimeYearInfo(result: (UiState<ArrayList<NewSaveTimeYearDTO>>) -> Unit )

    /*
    CheerBottomSheet
     */

    // 응원 댓글 받아오는 코드
    fun getCheerInfo(destinationUid :String, result: (UiState<List<String>>) -> Unit)
    // 응원 댓글 입력
    fun addCheerInfo(destinationUid: String, text:String, result: (UiState<List<String>>) -> Unit)
    // 응원 댓글 삭제
    fun deleteCheerInfo(destinationUid: String, text:String, result: (UiState<String>) -> Unit)


}