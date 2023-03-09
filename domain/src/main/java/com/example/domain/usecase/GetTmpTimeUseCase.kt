package com.example.domain.usecase

import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.FirstRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetTmpTimeUseCase(
    private val firstRepository: FirstRepository,
) {
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (ArrayList<TmpTimeModel>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val tmpTime = async (Dispatchers.IO) {
                firstRepository.getTmpTime()
            }
            onResult(tmpTime.await())
        }
    }
}