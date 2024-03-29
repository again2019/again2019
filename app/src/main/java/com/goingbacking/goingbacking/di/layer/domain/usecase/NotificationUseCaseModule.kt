package com.goingbacking.goingbacking.di.layer.domain.usecase

import com.example.domain.repository.NotificationRepository
import com.example.domain.usecase.myNotification.PostNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object NotificationUseCaseModule {

    @Provides
    fun providePostNotificationUseCase (
        notificationRepository: NotificationRepository
    ) : PostNotificationUseCase {
        return PostNotificationUseCase(notificationRepository)
    }
}