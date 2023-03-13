package com.example.domain.usecase.whatToDo.my

import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.repository.WhatToDoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMyWhatToDoMonthUseCase @Inject constructor(
    private val whatToDoRepository: WhatToDoRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        onResult : (UiState<ArrayList<WhatToDoMonthModel>>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                val whatToDoMonthModelList = async(Dispatchers.IO) {
                    whatToDoRepository.getMyWhatToDoMonthEntity()
                }.await()

                onResult(UiState.Success(whatToDoMonthModelList))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}