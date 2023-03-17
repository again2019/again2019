package com.example.domain.usecase.userInfo.my

import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateMyUserInfoUseCase (
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope: CoroutineScope,
        nickname : String,
        type : String,
        selected : List<String>,
        onResult: (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            onResult(UiState.Loading)
            kotlin.runCatching {
                userInfoRepository.updateUserInfoModel(nickname, type, selected)
            }.onSuccess {
                onResult(UiState.Success("Success"))
            }.onFailure {
                onResult(UiState.Failure("Failure"))
            }
        }

    }

}