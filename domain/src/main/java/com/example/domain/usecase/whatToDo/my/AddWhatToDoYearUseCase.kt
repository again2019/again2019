package com.example.domain.usecase.whatToDo.my

import com.example.domain.model.WhatToDoYearModel
import com.example.domain.repository.WhatToDoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWhatToDoYearUseCase (
    private val whatToDoRepository: WhatToDoRepository
){
    operator fun invoke (
        scope : CoroutineScope,
        whatToDoYearModel: WhatToDoYearModel,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                whatToDoRepository.addWhatToDoYearModel(whatToDoYearModel)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}