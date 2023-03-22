package com.example.domain.usecase.myDataStore.todayTotalTime

import com.example.domain.repository.DataStoreRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.flow.*

class GetTodayTotalTimeFromPreferencesUseCase (
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke () : Flow<UiState<Int?>> {

        return dataStoreRepository.getTodayTotalTimeFromPreferences().map {
            if (it != 0) {
                UiState.Success(it)
            } else {
                UiState.Failure("Failure")
            }
        }.catch {
            UiState.Failure("Failure")
        }

    }

//    suspend operator fun invoke () = flow {
//        emit(UiState.Loading)
//        dataStoreRepository.getTodayTotalTimeFromPreferences().collect {
//                emit(UiState.Success(it))
//            }
//        }.catch {
//            emit(UiState.Failure("Failure"))
//        }
    }
