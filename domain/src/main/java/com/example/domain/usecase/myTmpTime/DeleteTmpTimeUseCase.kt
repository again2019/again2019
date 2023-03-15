package com.example.domain.usecase.myTmpTime

import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteTmpTimeUseCase (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    operator fun invoke (
        scope: CoroutineScope,
        startTime: String,
        onResult: (UiState<String>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                tmpTimeRepository.deleteTmpTimeModel(startTime)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}