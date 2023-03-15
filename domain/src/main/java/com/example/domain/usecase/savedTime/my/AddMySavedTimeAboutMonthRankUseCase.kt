package com.example.domain.usecase.savedTime.my

import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel
import com.example.domain.repository.SavedTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMySavedTimeAboutMonthRankUseCase (
    private val savedTimeRepository: SavedTimeRepository
){
    operator fun invoke(
        scope: CoroutineScope,
        savedTimeAboutMonthRankModel: SavedTimeAboutRankModel,
        onResult: (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                savedTimeRepository.addMySavedTimeAboutMonthRankModel(savedTimeAboutMonthRankModel)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}