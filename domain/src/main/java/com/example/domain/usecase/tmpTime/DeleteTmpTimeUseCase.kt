package com.example.domain.usecase.tmpTime

import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteTmpTimeUseCase @Inject constructor (
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
                UiState.Success("Success")
            }.onFailure {
                UiState.Failure("Failure")
            }
        }
    }
}