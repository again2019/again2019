package com.example.domain.usecase.myAccount

import com.example.domain.repository.AccountRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetCurrentSessionUseCase (
    private val accountRepository: AccountRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<String>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                val currentSessiong = accountRepository.getCurrentSession()
                if (currentSessiong != null) {
                    onResult(UiState.Success(currentSessiong))
                } else {
                    onResult(UiState.Failure("Failure"))
                }
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }

}