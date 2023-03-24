package com.example.domain.usecase.myTmpTime

import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.DatabaseResult
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTmpTimeUseCase  (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    suspend operator fun invoke(
        currentTime: String,
        tmpTimeModel: TmpTimeModel,
        onResult: (DatabaseResult<String>) -> Unit,
    ) {
        tmpTimeRepository.addTmpTimeModel(
            currentTime,
            tmpTimeModel
        ) { result ->
            onResult(result)
        }
    }
}