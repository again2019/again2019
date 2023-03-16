package com.example.domain.usecase.myAccount

import com.example.domain.repository.AccountRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogOutUseCase (
    private val accountRepository: AccountRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<String>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                accountRepository.logOut()
                onResult(UiState.Success("Success"))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}