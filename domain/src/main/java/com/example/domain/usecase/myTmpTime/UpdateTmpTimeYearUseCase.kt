package com.example.domain.usecase.myTmpTime

import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.DatabaseResult
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateTmpTimeYearUseCase (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    suspend operator fun invoke (
        wakeUpTime1: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit,
    ) {
        tmpTimeRepository.updateTmpTimeYearModel(
            wakeUpTime1,
            count
        ) { result ->
            onResult(result)
        }
    }
}