package com.example.domain.usecase.savedTime.my

import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeYearModel
import com.example.domain.repository.SavedTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddMySavedTimeYearUseCase @Inject constructor(
    private val savedTimeRepository: SavedTimeRepository
){
    operator fun invoke(
        scope: CoroutineScope,
        savedTimeYearModel: SavedTimeYearModel,
        onResult: (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                savedTimeRepository.addMySavedTimeYearModel(savedTimeYearModel)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }

}