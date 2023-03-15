package com.example.domain.usecase.savedTime.common

import com.example.domain.repository.SavedTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateSavedTimeAboutMonthRankUseCase (
    private val savedTimeRepository: SavedTimeRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        yyyyMM: String,
        count: Double,
        onResult: (UiState<String>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                savedTimeRepository.updateSavedTimeAboutMonthRankModel(yyyyMM, count)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}