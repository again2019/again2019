package com.example.presentation.ui.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.myDataStore.recentDate.AddRecentDateFromPreferencesUseCase
import com.example.domain.usecase.myDataStore.recentDate.AddRecentDateFromProtoUseCase
import com.example.domain.usecase.myDataStore.recentDate.GetRecentDateFromPreferencesUseCase
import com.example.domain.usecase.myDataStore.recentDate.GetRecentDateFromProtoUseCase
import com.example.domain.usecase.myDataStore.todayTotalTime.AddTodayTotalTimeFromPreferencesUseCase
import com.example.domain.usecase.myDataStore.todayTotalTime.AddTodayTotalTimeFromProtoUseCase
import com.example.domain.usecase.myDataStore.todayTotalTime.GetTodayTotalTimeFromPreferencesUseCase
import com.example.domain.usecase.myDataStore.todayTotalTime.GetTodayTotalTimeFromProtoUseCase
import com.example.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val addRecentDateFromPreferencesUseCase: AddRecentDateFromPreferencesUseCase,
    private val getRecentDateFromPreferencesUseCase: GetRecentDateFromPreferencesUseCase,
    private val addRecentDateFromProtoUseCase: AddRecentDateFromProtoUseCase,
    private val getRecentDateFromProtoUseCase: GetRecentDateFromProtoUseCase,
    private val addTodayTotalTimeFromPreferencesUseCase: AddTodayTotalTimeFromPreferencesUseCase,
    private val getTodayTotalTimeFromPreferencesUseCase: GetTodayTotalTimeFromPreferencesUseCase,
    private val addTodayTotalTimeFromProtoUseCase: AddTodayTotalTimeFromProtoUseCase,
    private val getTodayTotalTimeFromProtoUseCase: GetTodayTotalTimeFromProtoUseCase,
) : ViewModel() {

    // recent date

    // preferences data store

    private val _addRecentDateFromPreferences = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addRecentDateFromPreferences : StateFlow<UiState<String>> = _addRecentDateFromPreferences

    suspend fun addRecentDateFromPreferences(date: String?) = viewModelScope.launch {
        addRecentDateFromPreferencesUseCase(date).collect {
            _addRecentDateFromPreferences.value = it
        }
    }

    private val _getRecentDateFromPreferences = MutableStateFlow<UiState<String?>>(UiState.Loading)
    val getRecentDateFromPreferences : StateFlow<UiState<String?>> = _getRecentDateFromPreferences

    suspend fun getRecentDateFromPreferences() = viewModelScope.launch {
        getRecentDateFromPreferencesUseCase().collect {
            _getRecentDateFromPreferences.value = it
        }
    }

    // proto data store

    private val _addRecentDateFromProto = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addRecentDateFromProto : StateFlow<UiState<String>> = _addRecentDateFromProto

    suspend fun addRecentDateFromProto(date: String?) = viewModelScope.launch {
        addRecentDateFromProtoUseCase(date).collect {
            _addRecentDateFromProto.value = it
        }
    }

    private val _getRecentDateFromProto = MutableStateFlow<UiState<String?>>(UiState.Loading)
    val getRecentDateFromProto : StateFlow<UiState<String?>> = _getRecentDateFromProto

    suspend fun getRecentDateFromProto() = viewModelScope.launch {
        getRecentDateFromProtoUseCase().collect {
            _getRecentDateFromProto.value = it
        }
    }

    // today total time

    // preferences data store

    private val _addTodayTotalTimeFromPreferences = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addTodayTotalTimeFromPreferences : StateFlow<UiState<String>> = _addTodayTotalTimeFromPreferences

    suspend fun addTodayTotalTimeFromPreferences(time: Int?) = viewModelScope.launch{
        addTodayTotalTimeFromPreferencesUseCase(time).collect {
            _addTodayTotalTimeFromPreferences.value = it
        }
    }

    private val _getTodayTotalTimeFromPreferences= MutableStateFlow<UiState<Int?>>(UiState.Loading)
    val getTodayTotalTimeFromPreferences : StateFlow<UiState<Int?>> = _getTodayTotalTimeFromPreferences

    suspend fun getTodayTotalTimeFromPreferences() = viewModelScope.launch {
        getTodayTotalTimeFromPreferencesUseCase().collect {
            _getTodayTotalTimeFromPreferences.value = it
        }
    }

    // proto data store

    private val _addTodayTotalTimeFromProto = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addTodayTotalTimeFromProto : StateFlow<UiState<String>> = _addTodayTotalTimeFromProto

    suspend fun addTodayTotalTimeFromProto(time: Int?) = viewModelScope.launch{
        addTodayTotalTimeFromProtoUseCase(time).collect {
            _addTodayTotalTimeFromProto.value = it
        }
    }

    private val _getTodayTotalTimeFromProto = MutableStateFlow<UiState<Int?>>(UiState.Loading)
    val getTodayTotalTimeFromProto : StateFlow<UiState<Int?>> = _getTodayTotalTimeFromProto

    suspend fun getTodayTotalTimeFromProto() = viewModelScope.launch{
        getTodayTotalTimeFromProtoUseCase().collect {
            _getTodayTotalTimeFromProto.value = it
        }
    }


}