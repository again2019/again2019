package com.example.domain.usecase.scheduleAndDate

import com.example.domain.model.DateModel
import com.example.domain.repository.ScheduleAndDateRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetDateUseCase  (
    private val scheduleAndDateRepository: ScheduleAndDateRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        yearMonth : String,
        onResult: (UiState<DateModel>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val date = async (Dispatchers.IO) {
                    scheduleAndDateRepository.getDateModel(yearMonth)
                }.await()

                if (date == null) onResult(UiState.Failure("Failure"))
                else {
                    onResult(UiState.Success(date))
                }
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}