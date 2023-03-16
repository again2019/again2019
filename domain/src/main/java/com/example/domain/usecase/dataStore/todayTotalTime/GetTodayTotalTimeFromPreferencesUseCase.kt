package com.example.domain.usecase.dataStore.todayTotalTime

import android.util.Log
import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GetTodayTotalTimeFromPreferencesUseCase (
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke () = flow {
        emit(UiState.Loading)
        dataStoreRepository.getTodayTotalTimeFromPreferences().collect {
                emit(UiState.Success(it))
            }
        }.catch {
            emit(UiState.Failure("Failure"))
        }
    }
