package com.goingbacking.goingbacking.UI.Main.Forth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goingbacking.goingbacking.Model.NewSaveTimeMonthDTO
import com.goingbacking.goingbacking.Model.NewSaveTimeYearDTO
import com.goingbacking.goingbacking.Repository.Forth.ForthRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForthViewModel @Inject constructor(
    val forthRepository: ForthRepositoryIF
) : ViewModel() {

    /*
    ForthMainFragment1
     */

    // month 기준으로 랭킹화
    private val _newSaveTimeMonth = MutableLiveData<UiState<ArrayList<NewSaveTimeMonthDTO>>>()
    val newSaveTimeMonth : LiveData<UiState<ArrayList<NewSaveTimeMonthDTO>>>
        get() = _newSaveTimeMonth

    fun getSaveTimeMonthInfo() = viewModelScope.launch {
        _newSaveTimeMonth.value = UiState.Loading
        forthRepository.getSaveTimeMonthInfo { _newSaveTimeMonth.value = it }
    }

    /*
    ForthMainFragment2
     */

    // year 기준으로 랭킹화
    private val _newSaveTimeYear = MutableLiveData<UiState<ArrayList<NewSaveTimeYearDTO>>>()
    val newSaveTimeYear : LiveData<UiState<ArrayList<NewSaveTimeYearDTO>>>
        get() = _newSaveTimeYear

    fun getSaveTimeYearInfo() = viewModelScope.launch {
        _newSaveTimeYear.value = UiState.Loading
        forthRepository.getSaveTimeYearInfo { _newSaveTimeYear.value = it }
    }


    /*
    CheerBottomSheet
     */


    // 응원 메시지 불러오기
    private val _cheerInfo = MutableLiveData<UiState<List<String>>>()
    val cheerInfo : LiveData<UiState<List<String>>>
        get() = _cheerInfo

    fun getCheerInfo(destinationUid : String)  {
        _cheerInfo.value = UiState.Loading
        forthRepository.getCheerInfo(destinationUid) { _cheerInfo.value = it }
    }

    // 응원 메시지 입력
    private val _addCheerInfo = MutableLiveData<UiState<List<String>>>()
    val addCheerInfo : LiveData<UiState<List<String>>>
        get() = _addCheerInfo

    fun addCheerInfo(destinationUid: String, text: String) = viewModelScope.launch {
        _addCheerInfo.value = UiState.Loading
        forthRepository.addCheerInfo(destinationUid, text) { _addCheerInfo.postValue(it) }
    }

    // 응원 메시지 삭제
    private val _deleteCheerInfo = MutableLiveData<UiState<String>>()

    fun deleteCheerInfo(destinationUid: String, text: String,) = viewModelScope.launch {
        _deleteCheerInfo.value = UiState.Loading
        forthRepository.deleteCheerInfo(destinationUid, text) { _deleteCheerInfo.postValue(it) }
    }




}