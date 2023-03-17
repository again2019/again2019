package com.example.domain.usecase.userInfo.other

import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteMyCheerToOtherUseCase (
    private val userInfoRepository: UserInfoRepository
){
    operator fun invoke (
        scope : CoroutineScope,
        destinationUid: String,
        text: String,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                userInfoRepository.deleteCheer(destinationUid, text)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}