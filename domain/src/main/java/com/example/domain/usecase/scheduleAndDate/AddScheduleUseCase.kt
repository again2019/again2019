package com.example.domain.usecase.scheduleAndDate

import com.example.domain.model.DateModel
import com.example.domain.model.ScheduleModel
import com.example.domain.repository.ScheduleAndDateRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddScheduleUseCase (
    private val scheduleAndDateRepository: ScheduleAndDateRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        path1 : String,
        path2 : String,
        scheduleModel: ScheduleModel,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                scheduleAndDateRepository.addScheduleModel(path1, path2, scheduleModel)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }

}