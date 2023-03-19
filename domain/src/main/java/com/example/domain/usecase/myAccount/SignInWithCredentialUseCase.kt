package com.example.domain.usecase.myAccount

import com.example.domain.repository.AccountRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInWithCredentialUseCase (
    private val accountRepository: AccountRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        token: String,
        onResult: (UiState<String>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                accountRepository.signInWithCredential(token)
                onResult(UiState.Success("Success"))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }

}