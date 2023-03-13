package com.example.domain.usecase.userInfo

import androidx.coordinatorlayout.widget.CoordinatorLayout.DispatchChangeEvent
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.repository.UserInfoRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddCheerUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
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

                // notification
                onResult(UiState.Success(destinationCheer))
            } catch (e: Exception) {
                onResult(UiState.Failure("Failure"))
            }
        }


    }
}