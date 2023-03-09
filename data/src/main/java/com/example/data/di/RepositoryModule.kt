package com.example.data.di


import com.example.data.repositoryImpl.First.UserInfoRepositoryImpl
import com.example.domain.repository.FirstRepository
import com.example.domain.repository.UserInfoRepository
import com.goingbacking.goingbacking.Repository.First.FirstRepositoryImpl
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
    abstract fun bindsUserInfoRepository(
        repository: UserInfoRepositoryImpl
    ) : UserInfoRepository

    @Singleton
    @Binds
    abstract fun bindsFirstRepository(
        repository: FirstRepositoryImpl
    ) : FirstRepository

}