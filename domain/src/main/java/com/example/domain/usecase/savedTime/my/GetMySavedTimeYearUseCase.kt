package com.example.domain.usecase.savedTime.my

import com.example.domain.model.SavedTimeYearModel
import com.example.domain.repository.SavedTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetMySavedTimeYearUseCase (
    private val savedTimeRepository: SavedTimeRepository
){
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<ArrayList<SavedTimeYearModel>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val savedTime = async (Dispatchers.IO) {
                    savedTimeRepository.getMySavedTimeYearModel()
                }.await()

                onResult(UiState.Success(savedTime))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}