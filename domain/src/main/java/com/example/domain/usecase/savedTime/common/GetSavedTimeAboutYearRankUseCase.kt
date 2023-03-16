package com.example.domain.usecase.savedTime.common

import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.repository.SavedTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetSavedTimeAboutYearRankUseCase (
    private val savedTimeRepository: SavedTimeRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<ArrayList<SavedTimeAboutRankModel>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val savedTimeAboutYearRank = async (Dispatchers.IO) {
                    savedTimeRepository.getSavedTimeAboutYearRankModel()
                }.await()

                onResult(UiState.Success(savedTimeAboutYearRank))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}