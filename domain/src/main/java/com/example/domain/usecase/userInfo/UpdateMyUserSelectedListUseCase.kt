package com.example.domain.usecase.userInfo

import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateMyUserSelectedListUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        selected: List<String>,
        onResult: (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                userInfoRepository.updateUserSelectedList(selected)
            }.onSuccess {
                UiState.Success("Success")
            }.onFailure {
                UiState.Failure("Failure")
            }
        }
    }

}