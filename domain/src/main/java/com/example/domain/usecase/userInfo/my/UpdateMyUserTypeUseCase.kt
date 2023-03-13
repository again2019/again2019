package com.example.domain.usecase.userInfo.my

import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateMyUserTypeUseCase  @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        userType: String,
        onResult: (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                userInfoRepository.updateUserType(userType)
            }.onSuccess {
                UiState.Success("Success")
            }.onFailure {
                UiState.Failure("Failure")
            }
        }
    }

}