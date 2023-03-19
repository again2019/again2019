package com.example.domain.usecase.myDataStore.todayTotalTime

import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AddTodayTotalTimeFromProtoUseCase (
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(
        time: Int?,
    ) = flow {
        emit(UiState.Loading)
        if (time != null) {
            dataStoreRepository.addTodayTotalTimeFromProto(time)
            emit(UiState.Success("Success"))
        } else {
            emit(UiState.Failure("Failure"))
        }
    }.catch {
        emit(UiState.Failure("Failure"))
    }

}