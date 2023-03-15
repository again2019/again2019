package com.goingbacking.goingbacking.di


import com.example.data.repositoryImpl.*
import com.example.domain.repository.*

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsSavedTimeRepository(
        repository: SavedTimeRepositoryImpl
    ) : SavedTimeRepository

    @Singleton
    @Binds
    abstract fun bindsTmpTimeRepository(
        repository: TmpTimeRepositoryImpl
    ) : TmpTimeRepository

    @Singleton
    @Binds
    abstract fun bindsUserInfoRepository(
        repository: UserInfoRepositoryImpl
    ) : UserInfoRepository

    @Singleton
    @Binds
    abstract fun bindsWhatToDoRepository(
        repository: WhatToDoRepositoryImpl
    ) : WhatToDoRepository

    @Singleton
    @Binds
    abstract fun bindsScheduleAndDateRepository(
        repository: ScheduleAndDateRepositoryImpl
    ) : ScheduleAndDateRepository

    @Singleton
    @Binds
    abstract fun bindsAccountRepository(
        repository: AccountRepositoryImpl
    ) : AccountRepository

    @Singleton
    @Binds
    abstract fun bindsNotificationRepository(
        repository: NotificationRepositoryImpl
    ) : NotificationRepository

}


