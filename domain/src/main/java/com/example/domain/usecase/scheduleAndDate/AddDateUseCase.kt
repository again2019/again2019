package com.example.domain.usecase.scheduleAndDate

import com.example.domain.model.DateModel
import com.example.domain.model.WhatToDoYearModel
import com.example.domain.repository.ScheduleAndDateRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDateUseCase (
    private val scheduleAndDateRepository: ScheduleAndDateRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        yearMonth : String,
        date: DateModel,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                scheduleAndDateRepository.addDateModel(yearMonth, date)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }

}