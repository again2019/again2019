package com.example.domain.usecase.whatToDo.my

import com.example.domain.repository.WhatToDoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateWhatToDoMonthUseCase (
    private val whatToDoRepository: WhatToDoRepository
){
    operator fun invoke (
        scope : CoroutineScope,
        yyyyMM: String,
        whatToDo: String,
        count: Double,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                whatToDoRepository.updateWhatToDoMonthModel(yyyyMM, whatToDo, count)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }

}