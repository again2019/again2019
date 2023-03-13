package com.example.domain.usecase.userInfo

import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateLikeButtonUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
){
    operator fun invoke (
        scope : CoroutineScope,
        destinationUid: String,
        state: String,
        onResult : (UiState<String>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                onResult(UiState.Loading)
                onResult(UiState.Success(userInfoRepository.updateLikeButton(destinationUid, state)))
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}