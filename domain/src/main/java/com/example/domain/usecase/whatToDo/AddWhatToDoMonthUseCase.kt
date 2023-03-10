package com.example.domain.usecase.whatToDo

import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.repository.WhatToDoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddWhatToDoMonthUseCase @Inject constructor(
    private val whatToDoRepository: WhatToDoRepository
){
    operator fun invoke (
        scope : CoroutineScope,
        whatToDoMonthModel: WhatToDoMonthModel,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                whatToDoRepository.addWhatToDoMonthModel(whatToDoMonthModel)
            }.onSuccess {
                UiState.Success("Success")
            }.onFailure {
                UiState.Failure("Failure")
            }
        }
    }
}