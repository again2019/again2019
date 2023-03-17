package com.example.domain.usecase.myDataStore.todayTotalTime

import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AddTodayTotalTimeFromPreferencesUseCase (
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(
        time: Int?,
    ) = flow {
        emit(UiState.Loading)
        if (time != null) {
            dataStoreRepository.addTodayTotalTimeFromPreferences(time)
            emit(UiState.Success("Success"))
        } else {
            emit(UiState.Failure("Failure"))
        }
    }.catch {
        emit(UiState.Failure("Failure"))
    }
}