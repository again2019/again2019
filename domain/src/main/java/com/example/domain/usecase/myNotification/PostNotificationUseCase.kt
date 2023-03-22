package com.example.domain.usecase.myNotification

import com.example.domain.model.NotificationModel
import com.example.domain.repository.NotificationRepository
import com.example.domain.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostNotificationUseCase  (
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(
        scope: CoroutineScope,
        notificationModel: NotificationModel,
        onResult: (UiState<String>) -> Unit,
    ) {

    }


}