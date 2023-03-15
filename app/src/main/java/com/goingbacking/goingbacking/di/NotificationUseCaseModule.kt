package com.goingbacking.goingbacking.di

import com.example.domain.repository.NotificationRepository
import com.example.domain.usecase.notification.PostNotificationUseCase
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