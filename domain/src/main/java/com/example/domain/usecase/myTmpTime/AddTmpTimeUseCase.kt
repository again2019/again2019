package com.example.domain.usecase.myTmpTime

import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTmpTimeUseCase  (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    operator fun invoke(
        scope: CoroutineScope,
        currentTime: String,
        tmpTimeModel: TmpTimeModel,
        onResult: (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                tmpTimeRepository.addTmpTimeModel(currentTime, tmpTimeModel)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }


}