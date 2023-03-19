package com.example.domain.usecase.myDataStore.todayTotalTime

import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.flow.*

class GetTodayTotalTimeFromProtoUseCase (
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke () = flow {
        emit(UiState.Loading)
        dataStoreRepository.getTodayTotalTimeFromProto()
            .catch {
            emit(UiState.Failure("Failure"))
        }
            .collect {
            emit(UiState.Success(it))
        }
    }
}