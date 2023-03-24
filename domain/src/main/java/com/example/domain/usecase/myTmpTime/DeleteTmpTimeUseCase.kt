package com.example.domain.usecase.myTmpTime

import com.example.domain.repository.TmpTimeRepository
import com.example.domain.util.DatabaseResult
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteTmpTimeUseCase (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    suspend operator fun invoke (
        startTime: String,
        onResult: (DatabaseResult<String>) -> Unit,
    ) {
        tmpTimeRepository.deleteTmpTimeModel(
            startTime
        ) { result ->
            onResult(result)
        }
    }
}