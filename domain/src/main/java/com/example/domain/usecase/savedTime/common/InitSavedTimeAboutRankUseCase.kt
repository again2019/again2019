package com.example.domain.usecase.savedTime.common

import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.repository.SavedTimeRepository
import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InitSavedTimeAboutRankUseCase @Inject constructor (
    private val savedTimeRepository: SavedTimeRepository,
) {
    operator fun invoke(
        scope: CoroutineScope,
        savedTimeAboutRankModel: SavedTimeAboutRankModel,
        onResult: (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                savedTimeRepository.initSavedTimeAboutRankModel(savedTimeAboutRankModel)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}