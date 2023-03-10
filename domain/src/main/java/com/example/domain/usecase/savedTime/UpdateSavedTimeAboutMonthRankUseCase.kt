package com.example.domain.usecase.savedTime

import com.example.domain.repository.SavedTimeRepository
import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateSavedTimeAboutMonthRankUseCase @Inject constructor (
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
                UiState.Success("Success")
            }.onFailure {
                UiState.Failure("Failure")
            }
        }
    }
}