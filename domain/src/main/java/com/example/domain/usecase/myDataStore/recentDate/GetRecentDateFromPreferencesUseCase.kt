package com.example.domain.usecase.myDataStore.recentDate

import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.flow.*

class GetRecentDateFromPreferencesUseCase (
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke () = flow {
        emit(UiState.Loading)
        dataStoreRepository.getRecentDateFromPreferences().collectLatest {
            emit(UiState.Success(it))
        }
    }.catch {
        emit(UiState.Failure("Failure"))
    }
}