package com.example.domain.usecase.myDataStore.todayTotalTime

import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.flow.*

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
