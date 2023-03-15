package com.example.domain.usecase.userInfo.my

import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetMyNickNameUseCase (
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        onResult : (UiState<String>) -> Unit,
    ) {

        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                val userNickName = async(Dispatchers.IO) {
                    userInfoRepository.getMyUserInfoModel().userNickName
                }.await()

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