package com.example.domain.usecase.userInfo

import com.example.domain.model.TmpTimeModel
import com.example.domain.model.UserInfoModel
import com.example.domain.repository.UserInfoRepository
import com.goingbacking.goingbacking.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UpdateUserInfoUseCase(
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
                UiState.Success("Success")
            }.onFailure {
                UiState.Failure("Failure")
            }
        }

    }

}