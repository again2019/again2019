package com.example.domain.usecase.tmpTime

import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetTmpTimeUseCase @Inject constructor (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<ArrayList<TmpTimeModel>>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)

                // withContext로 대체 가능하긴 함
                val tmpTime = async (Dispatchers.IO) {
                    tmpTimeRepository.getTmpTimeModel()
                }.await()

                onResult(UiState.Success(tmpTime))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}