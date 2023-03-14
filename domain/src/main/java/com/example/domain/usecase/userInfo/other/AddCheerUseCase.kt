package com.example.domain.usecase.userInfo.other

import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddCheerUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val notificationRepository: NotificationRepository,
){
    operator fun invoke (
        scope : CoroutineScope,
        destinationUid: String,
        text: String,
        onResult : (UiState<List<String>>) -> Unit,
    ) {
        scope.launch(Dispatchers.Main) {

            try {
                onResult(UiState.Loading)

                // async로 병렬화 가능하지 않을까?
                val myUserInfo = withContext(Dispatchers.IO) {
                    userInfoRepository.getMyUserInfoModel()
                }
                val cheer = myUserInfo.uid + ":" + myUserInfo.userNickName + ":" + text
                val destinationCheer = withContext(Dispatchers.IO) {
                    userInfoRepository.updateCheerList(destinationUid, cheer)
                }
                onResult(UiState.Success(destinationCheer))

                NotificationModel(
                    NotificationModel.NotificationDataModel(
                        "응원 메시지",
                        "응원 메시지를 확인해보세요!\n" + myUserInfo.userNickName + ": " + text),
                    myUserInfo.token!!
                ).also {
                    notificationRepository.postNotificationModel(it)
                }

                // notification
            } catch (e: Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }


    }
}