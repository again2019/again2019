package com.example.domain.usecase.myTmpTime

import com.example.domain.util.DatabaseResult
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository

class GetTmpTimeUseCase  (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    suspend operator fun invoke (
        onResult: (DatabaseResult<List<TmpTimeModel>>) -> Unit
    ) {
        tmpTimeRepository.getTmpTimeModel { result ->
            onResult(result)
        }
    }
}