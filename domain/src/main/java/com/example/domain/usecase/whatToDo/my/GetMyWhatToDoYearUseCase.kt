package com.example.domain.usecase.whatToDo.my

import com.example.domain.model.WhatToDoYearModel
import com.example.domain.repository.WhatToDoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetMyWhatToDoYearUseCase (
    private val whatToDoRepository: WhatToDoRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        onResult : (UiState<ArrayList<WhatToDoYearModel>>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                val whatToDoYearModelList = async(Dispatchers.IO) {
                    whatToDoRepository.getMyWhatToDoYearEntity()
                }.await()

                onResult(UiState.Success(whatToDoYearModelList))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}