package com.example.presentation.ui.dataStore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.dataStore.recentDate.AddRecentDateFromPreferencesUseCase
import com.example.domain.usecase.dataStore.recentDate.AddRecentDateFromProtoUseCase
import com.example.domain.usecase.dataStore.recentDate.GetRecentDateFromPreferencesUseCase
import com.example.domain.usecase.dataStore.recentDate.GetRecentDateFromProtoUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.AddTodayTotalTimeFromPreferencesUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.AddTodayTotalTimeFromProtoUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.GetTodayTotalTimeFromPreferencesUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.GetTodayTotalTimeFromProtoUseCase
import com.example.domain.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
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
        addRecentDateFromPreferencesUseCase(date).collectLatest {
            _addRecentDateFromPreferences.value = it
        }
    }

    private val _getRecentDateFromPreferences = MutableStateFlow<UiState<String?>>(UiState.Loading)
    val getRecentDateFromPreferences : StateFlow<UiState<String?>> = _getRecentDateFromPreferences

    suspend fun getRecentDateFromPreferences() = viewModelScope.launch {
        getRecentDateFromPreferencesUseCase().collectLatest {
            _getRecentDateFromPreferences.value = it
        }
    }

    // proto data store

    private val _addRecentDateFromProto = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addRecentDateFromProto : StateFlow<UiState<String>> = _addRecentDateFromProto

    suspend fun addRecentDateFromProto(date: String?) = viewModelScope.launch {
        addRecentDateFromProtoUseCase(date).collectLatest {
            _addRecentDateFromProto.value = it
        }
    }

    private val _getRecentDateFromProto = MutableStateFlow<UiState<String?>>(UiState.Loading)
    val getRecentDateFromProto : StateFlow<UiState<String?>> = _getRecentDateFromProto

    suspend fun getRecentDateFromProto() = viewModelScope.launch {
        getRecentDateFromProtoUseCase().collectLatest {
            _getRecentDateFromProto.value = it
        }
    }

    // today total time

    // preferences data store

    private val _addTodayTotalTimeFromPreferences = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addTodayTotalTimeFromPreferences : StateFlow<UiState<String>> = _addTodayTotalTimeFromPreferences

    suspend fun addTodayTotalTimeFromPreferences(time: Int?) = viewModelScope.launch{
        addTodayTotalTimeFromPreferencesUseCase(time).collectLatest {
            _addTodayTotalTimeFromPreferences.value = it
        }
    }

    private val _getTodayTotalTimeFromPreferences= MutableStateFlow<UiState<Int?>>(UiState.Loading)
    val getTodayTotalTimeFromPreferences : StateFlow<UiState<Int?>> = _getTodayTotalTimeFromPreferences

    suspend fun getTodayTotalTimeFromPreferences() = viewModelScope.launch {
        getTodayTotalTimeFromPreferencesUseCase().collectLatest {
            _getTodayTotalTimeFromPreferences.value = it
        }
    }

    // proto data store

    private val _addTodayTotalTimeFromProto = MutableStateFlow<UiState<String>>(UiState.Loading)
    val addTodayTotalTimeFromProto : StateFlow<UiState<String>> = _addTodayTotalTimeFromProto

    suspend fun addTodayTotalTimeFromProto(time: Int?) = viewModelScope.launch{
        addTodayTotalTimeFromProtoUseCase(time).collectLatest {
            _addTodayTotalTimeFromProto.value = it
        }
    }

    private val _getTodayTotalTimeFromProto = MutableStateFlow<UiState<Int?>>(UiState.Loading)
    val getTodayTotalTimeFromProto : StateFlow<UiState<Int?>> = _getTodayTotalTimeFromProto

    suspend fun getTodayTotalTimeFromProto() = viewModelScope.launch{
        getTodayTotalTimeFromProtoUseCase().collectLatest {
            _getTodayTotalTimeFromProto.value = it
        }
    }


}