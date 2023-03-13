package com.example.domain.usecase.userInfo

import com.example.domain.model.UserInfoModel
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMyUserInfoUseCase @Inject constructor (
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        onResult : (UiState<UserInfoModel>) -> Unit,
    ) {

        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                val userInfoModel = async(Dispatchers.IO) {
                    userInfoRepository.getMyUserInfoModel()
                }.await()

                when (userInfoModel != UserInfoModel()) {
                    true -> {
                        onResult(UiState.Success(userInfoModel))
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