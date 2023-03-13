package com.example.domain.usecase.scheduleAndDate

import com.example.domain.model.ScheduleModel
import com.example.domain.repository.ScheduleAndDateRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class GetAllSchedulesUseCase @Inject constructor (
    private val scheduleAndDateRepository: ScheduleAndDateRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<MutableMap<LocalDate, List<ScheduleModel>>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val allSchedules = async (Dispatchers.IO) {
                    scheduleAndDateRepository.getAllScheduleModels()
                }.await()

                onResult(UiState.Success(allSchedules))

            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }

    }
}