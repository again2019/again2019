package com.example.presentation.ui.tutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel
import com.example.domain.usecase.savedTime.my.*
import com.example.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val addMySavedTimeDayUseCase: AddMySavedTimeDayUseCase,
    private val addMySavedTimeMonthUseCase: AddMySavedTimeMonthUseCase,
    private val addMySavedTimeYearUseCase: AddMySavedTimeYearUseCase,
) : ViewModel() {

    // --- TutorialActivity ---

    private val _addMySavedTimeDayModel = MutableLiveData<UiState<String>>()

    fun addMySavedTimeDay(savedTimeDayModel: SavedTimeDayModel) {
        addMySavedTimeDayUseCase(viewModelScope, savedTimeDayModel) {
            _addMySavedTimeDayModel.postValue(it)
        }
    }


    private val _addMySavedTimeMonthModel = MutableLiveData<UiState<String>>()

    fun addMySavedTimeMonth(savedTimeMonthModel: SavedTimeMonthModel) {
        addMySavedTimeMonthUseCase(viewModelScope, savedTimeMonthModel) {
            _addMySavedTimeMonthModel.postValue(it)
        }
    }

    private val _addMySavedTimeYearModel = MutableLiveData<UiState<String>>()

    fun addMySavedTimeYear(savedTimeYearModel: SavedTimeYearModel) {
        addMySavedTimeYearUseCase(viewModelScope, savedTimeYearModel) {
            _addMySavedTimeYearModel.postValue(it)
        }
    }



}