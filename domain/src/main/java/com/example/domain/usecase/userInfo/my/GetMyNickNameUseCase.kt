package com.example.domain.usecase.userInfo.my

import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.*

class GetMyNickNameUseCase (
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch {
            try {
                onResult(UiState.Loading)
                val userNickName = withContext(Dispatchers.IO) {
                    userInfoRepository.getMyUserInfoModel().userNickName
                }

                when (userNickName != null) {
                    true -> {
                        onResult(UiState.Success(userNickName))
                    }
                    else -> {
                        onResult(UiState.Failure("Failure"))
                    }
                }
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}


