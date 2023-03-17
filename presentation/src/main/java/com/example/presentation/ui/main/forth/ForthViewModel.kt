package com.example.presentation.ui.main.forth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.usecase.savedTime.common.GetSavedTimeAboutMonthRankUseCase
import com.example.domain.usecase.savedTime.common.GetSavedTimeAboutYearRankUseCase
import com.example.domain.usecase.userInfo.other.AddMyCheerToOtherUseCase
import com.example.domain.usecase.userInfo.other.DeleteMyCheerToOtherUseCase
import com.example.domain.usecase.userInfo.other.GetOtherCheerListUseCase
import com.example.domain.util.UiState
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ForthViewModel @Inject constructor(
    private val getSavedTimeAboutMonthRankUseCase: GetSavedTimeAboutMonthRankUseCase,
    private val getSavedTimeAboutYearRankUseCase: GetSavedTimeAboutYearRankUseCase,
    private val getCheerListUseCase: GetOtherCheerListUseCase,
    private val addCheerUseCase: AddMyCheerToOtherUseCase,
    private val deleteCheerUseCase: DeleteMyCheerToOtherUseCase,
    private val queryRankingInfo : Query,
) : ViewModel() {

    //
//        val flow = Pager(
//            PagingConfig(
//                pageSize = PAGE_SIZE.toInt()
//                )
//            ) {
//                RankPagingSource(queryRankingInfo)
//            }.flow.cachedIn(viewModelScope)


    //


    /*
    ForthMainFragment1
     */

    // month 기준으로 랭킹화
    private val _newSaveTimeMonth = MutableLiveData<UiState<ArrayList<SavedTimeAboutRankModel>>>()
    val newSaveTimeMonth : LiveData<UiState<ArrayList<SavedTimeAboutRankModel>>>
        get() = _newSaveTimeMonth

    fun getSaveTimeMonthInfo()  {
        getSavedTimeAboutMonthRankUseCase(viewModelScope) {
            _newSaveTimeMonth.value = it
        }
    }

    /*
    ForthMainFragment2
     */

    // year 기준으로 랭킹화
    private val _newSaveTimeYear = MutableLiveData<UiState<ArrayList<SavedTimeAboutRankModel>>>()
    val newSaveTimeYear : LiveData<UiState<ArrayList<SavedTimeAboutRankModel>>>
        get() = _newSaveTimeYear

    fun getSaveTimeYearInfo() {
        getSavedTimeAboutYearRankUseCase(viewModelScope) {
            _newSaveTimeYear.value = it
        }
    }

    /*
    CheerBottomSheet
     */


    // 응원 메시지 불러오기
    private val _cheerInfo = MutableLiveData<UiState<List<String>>>()
    val cheerInfo : LiveData<UiState<List<String>>>
        get() = _cheerInfo

    fun getCheerInfo(destinationUid : String)  {
        getCheerListUseCase(viewModelScope, destinationUid) {
            _cheerInfo.postValue(it)
        }
    }

    // 응원 메시지 입력
    private val _addCheerInfo = MutableLiveData<UiState<List<String>>>()
    val addCheerInfo : LiveData<UiState<List<String>>>
        get() = _addCheerInfo

    fun addCheerInfo(destinationUid: String, text: String)  {
        addCheerUseCase(viewModelScope, destinationUid, text) {
            _addCheerInfo.postValue(it)
        }

    }

    // 응원 메시지 삭제
    private val _deleteCheerInfo = MutableLiveData<UiState<String>>()

    fun deleteCheerInfo(destinationUid: String, text: String,){
        deleteCheerUseCase(viewModelScope, destinationUid, text) {
            _deleteCheerInfo.postValue(it)
        }
    }




}