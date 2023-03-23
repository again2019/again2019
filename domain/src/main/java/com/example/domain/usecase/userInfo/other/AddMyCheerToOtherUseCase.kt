package com.example.domain.usecase.userInfo.other

import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddMyCheerToOtherUseCase (
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

                // 개인 정보 가져옴
                val myUserInfo = withContext(Dispatchers.IO) {
                    userInfoRepository.getMyUserInfoModel()
                }

                val cheer = myUserInfo.uid + ":" + myUserInfo.userNickName + ":" + text
                val notificationModel = NotificationModel(
                    NotificationModel.NotificationDataModel(
                        "응원 메시지",
                        "응원 메시지를 확인해보세요!\n" + myUserInfo.userNickName + ": " + text),
                    myUserInfo.token!!
                )

                // 1. 응원 메시지를 상대 cheerList에 추가
                val destinationCheer = withContext(Dispatchers.IO) {
                    userInfoRepository.updateCheerList(destinationUid, cheer)
                }

                // 2. 응원 메시지 전달
                notificationRepository.postNotificationModel(notificationModel) {

                }

                // 1. 2를 병렬 처리 -> 1, 2 리턴이 둘 다 success일 경우에 onResult(UiState.Success)) 실행
                onResult(UiState.Success(destinationCheer))

            } catch (e: Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }


    }
}