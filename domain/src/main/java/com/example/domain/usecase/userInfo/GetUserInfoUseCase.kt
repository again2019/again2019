package com.example.domain.usecase.userInfo

import com.example.domain.model.UserInfoModel
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor (
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        onResult : (UiState<UserInfoModel>) -> Unit,
    ) {

        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                val userInfo = async(Dispatchers.IO) {
                    userInfoRepository.getUserInfoModel()
                }.await()

                when (userInfo != UserInfoModel()) {
                    true -> {
                        onResult(UiState.Success(userInfo))
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