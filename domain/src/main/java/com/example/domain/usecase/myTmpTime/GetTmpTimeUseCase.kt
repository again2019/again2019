package com.example.domain.usecase.myTmpTime

import com.example.domain.util.Response
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository

class GetTmpTimeUseCase  (
    private val tmpTimeRepository: TmpTimeRepository,
) {
    suspend operator fun invoke (
        result: (Response<List<TmpTimeModel>>) -> Unit
    ) {
        tmpTimeRepository.getTmpTimeModel {  response ->
            result(response)
        }
    }
}