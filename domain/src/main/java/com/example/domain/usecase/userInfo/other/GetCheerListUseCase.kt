package com.example.domain.usecase.userInfo.other

import com.example.domain.model.UserInfoModel
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GetCheerListUseCase  (
    private val userInfoRepository: UserInfoRepository
) {
    operator fun invoke (
        scope : CoroutineScope,
        destinationUid : String,
        onResult : (UiState<List<String>>) -> Unit,
    ) {

        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                val userInfoModel = async(Dispatchers.IO) {
                    userInfoRepository.getOtherUserInfoModel(destinationUid)
                }.await()

                when (userInfoModel != UserInfoModel()) {
                    true -> {
                        onResult(UiState.Success(userInfoModel.cheers))
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