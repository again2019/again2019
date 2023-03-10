package com.example.domain.usecase.savedTime

import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.SavedTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetSavedTimeMonthUseCase @Inject constructor(
    private val savedTimeRepository: SavedTimeRepository
){
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<ArrayList<SavedTimeMonthModel>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val savedTime = async (Dispatchers.IO) {
                    savedTimeRepository.getSavedTimeMonthModel()
                }.await()

                onResult(UiState.Success(savedTime))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}