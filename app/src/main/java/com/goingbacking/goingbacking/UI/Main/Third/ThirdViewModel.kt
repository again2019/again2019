package com.goingbacking.goingbacking.UI.Main.Third

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.Repository.Third.ThirdRepositoryIF
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ThirdViewModel @Inject constructor(
    val thirdRepository: ThirdRepositoryIF
) : ViewModel() {


    private val _dateDTOs = MutableLiveData<UiState<String>>()

    fun addDateInfo(yearMonth: String, date: DateDTO) {
        _dateDTOs.value = UiState.Loading
        thirdRepository.addDateInfo(yearMonth, date) { _dateDTOs.value = it}
    }


}