package com.example.domain.usecase.userInfo.other

import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateLikeButtonUseCase (
    private val userInfoRepository: UserInfoRepository,
    private val notificationRepository: NotificationRepository,
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

                val myUserInfo = withContext(Dispatchers.IO) {
                    userInfoRepository.getMyUserInfoModel()
                }
                onResult(UiState.Success(userInfoRepository.updateLikeButton(destinationUid, state)))

                if (state == "plus") {
                    NotificationModel(
                        NotificationModel.NotificationDataModel(
                            "좋아요",
                            myUserInfo.userNickName!!+ "님의 좋아요 수가 늘었습니다! 확인해보세요!"
                        ),
                        myUserInfo.token!!
                    ).also {
                        notificationRepository.postNotificationModel(it)
                    }

                }
            } catch (e : Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }
    }
}