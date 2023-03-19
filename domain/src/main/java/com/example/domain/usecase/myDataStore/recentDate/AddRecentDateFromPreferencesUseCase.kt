package com.example.domain.usecase.myDataStore.recentDate

import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AddRecentDateFromPreferencesUseCase (
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(
        date: String?,
    )  = flow {
        emit(UiState.Loading)
        if (date != null) {
            dataStoreRepository.addRecentDateFromPreferences(date)
            emit(UiState.Success("Success"))
        } else {
            emit(UiState.Failure("Failure"))
        }
    }.catch {
        emit(UiState.Failure("Failure"))
    }
}




