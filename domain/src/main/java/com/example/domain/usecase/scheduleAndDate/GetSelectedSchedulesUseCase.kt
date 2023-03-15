package com.example.domain.usecase.scheduleAndDate

import com.example.domain.model.DateModel
import com.example.domain.model.ScheduleModel
import com.example.domain.repository.ScheduleAndDateRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetSelectedSchedulesUseCase (
    private val scheduleAndDateRepository: ScheduleAndDateRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        yearMonth : String,
        date : String,
        onResult: (UiState<List<ScheduleModel>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val selectedSchedules = async (Dispatchers.IO) {
                    scheduleAndDateRepository.getSelectedScheduleModels(yearMonth, date)
                }.await()

                onResult(UiState.Success(selectedSchedules))

            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }

    }
}