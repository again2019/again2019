package com.example.domain.usecase.scheduleAndDate

import com.example.domain.model.DateModel
import com.example.domain.model.ScheduleModel
import com.example.domain.repository.ScheduleAndDateRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class DeleteSchedulesUseCase @Inject constructor(
    private val scheduleAndDateRepository: ScheduleAndDateRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        scheduleDate: String,
        timeStamp: String,
        onResult: (UiState<MutableMap<LocalDate, List<ScheduleModel>>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val scheduleModels = async (Dispatchers.IO) {
                    scheduleAndDateRepository.deleteScheduleModels(scheduleDate, timeStamp)
                }.await()

                onResult(UiState.Success(scheduleModels))

            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}