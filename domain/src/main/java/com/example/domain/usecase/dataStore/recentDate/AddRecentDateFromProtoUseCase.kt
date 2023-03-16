package com.example.domain.usecase.dataStore.recentDate

import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AddRecentDateFromProtoUseCase (
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(
        date: String?,
    ) = flow {
        emit(UiState.Loading)
        if (date != null) {
            dataStoreRepository.addRecentDateFromProto(date)
            emit(UiState.Success("Success"))
        } else {
            emit(UiState.Failure("Failure"))
        }
    }.catch {
        emit(UiState.Failure("Failure"))
    }
}