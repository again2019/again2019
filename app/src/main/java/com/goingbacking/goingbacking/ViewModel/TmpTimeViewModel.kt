package com.goingbacking.goingbacking.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Repository.TmpTimeRepository
import com.goingbacking.goingbacking.Repository.TmpTimeRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TmpTimeViewModel @Inject constructor(
    val tmpTimeRepository: TmpTimeRepositoryIF) : ViewModel(){

    private val _tmpTimeDTOs = MutableLiveData<UiState<ArrayList<TmpTimeDTO>>>()
    val tmpTimeDTOs : LiveData<UiState<ArrayList<TmpTimeDTO>>>
        get() = _tmpTimeDTOs

    fun getTmpTimeInfo() {
        _tmpTimeDTOs.value = UiState.Loading
        tmpTimeRepository.getTmpTimeInfo { _tmpTimeDTOs.value = it }

    }





    }