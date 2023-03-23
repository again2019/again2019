package com.example.domain.usecase.userInfo.other

import android.util.Log
import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.Response
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateOtherLikeButtonUseCase (
    private val userInfoRepository: UserInfoRepository,
    private val notificationRepository: NotificationRepository,
){
    suspend operator fun invoke (
        destinationUid: String,
        state: String,
        onResult : (String) -> Unit,
        onError : (String?) -> Unit,
    ) : Flow<String> {

        val myUserInfo = withContext(Dispatchers.IO) {
            userInfoRepository.getMyUserInfoModel()
        }
        onResult(userInfoRepository.updateLikeButton(destinationUid, state))

        var notificationModel = NotificationModel(
            NotificationModel.NotificationDataModel(
                "", ""
            ), ""
        )

        if(state == "plus") {
            notificationModel = NotificationModel(
                NotificationModel.NotificationDataModel(
                    "좋아요",
                    myUserInfo.userNickName!! + "님의 좋아요 수가 늘었습니다! 확인해보세요!"
                ),
                myUserInfo.token!!
            )
        }


        return notificationRepository.postNotificationModel(
                notificationModel,
            onError = { onError(it) }
            )
    }
}