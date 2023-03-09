package com.example.domain.di

import com.example.domain.repository.FirstRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.usecase.GetTmpTimeUseCase
import com.example.domain.usecase.userInfo.GetUserInfoUseCase
import com.example.domain.usecase.userInfo.UpdateUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UserInfoUseCaseModule {
    @Provides
    fun provideGetUserInfoUseCase(repository: UserInfoRepository): GetUserInfoUseCase {
        return GetUserInfoUseCase(repository)
    }

    @Provides
    fun provideUpdateUserInfoUseCase(repository: UserInfoRepository): UpdateUserInfoUseCase {
        return UpdateUserInfoUseCase(repository)
    }

}