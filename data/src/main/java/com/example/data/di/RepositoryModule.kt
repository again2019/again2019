package com.example.data.di


import com.example.data.repositoryImpl.userInfoRepository.UserInfoRepositoryImpl
import com.example.data.repositoryImpl.whatToDoRepository.WhatToDoRepositoryImpl
import com.example.domain.repository.FirstRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.repository.WhatToDoRepository
import com.goingbacking.goingbacking.repository.first.FirstRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
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
    abstract fun bindsFirstRepository(
        repository: FirstRepositoryImpl
    ) : FirstRepository

}