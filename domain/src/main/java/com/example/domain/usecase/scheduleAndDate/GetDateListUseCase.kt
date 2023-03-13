package com.example.domain.usecase.scheduleAndDate

import com.example.domain.model.DateModel
import com.example.domain.repository.ScheduleAndDateRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetDateListUseCase @Inject constructor (
private val scheduleAndDateRepository: ScheduleAndDateRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        yearMonth : String,
        onResult: (UiState<List<String>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                onResult(UiState.Success(scheduleAndDateRepository.getDateModelList(yearMonth)))

            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}