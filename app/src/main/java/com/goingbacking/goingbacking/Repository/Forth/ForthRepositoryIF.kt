package com.goingbacking.goingbacking.Repository.Forth

import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.NewSaveTimeYearDTO
import com.goingbacking.goingbacking.util.UiState

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