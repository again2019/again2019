package com.example.domain.usecase.myTmpTime

import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateTmpTimeDayUseCase @Inject constructor (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    operator fun invoke (
        scope: CoroutineScope,
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double,
        onResult: (UiState<String>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                tmpTimeRepository.updateTmpTimeDayModel(wakeUpTime1, wakeUpTime2, count)
            }.onSuccess {
                UiState.Success("Success")
            }.onFailure {
                UiState.Failure("Failure")
            }
        }
    }
}