package com.example.domain.usecase.myAccount

import com.example.domain.repository.AccountRepository
import com.example.domain.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GetGsoUseCase (
    private val accountRepository: AccountRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        onResult: (UiState<GoogleSignInOptions>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                onResult(UiState.Success(accountRepository.getGso()))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }

}