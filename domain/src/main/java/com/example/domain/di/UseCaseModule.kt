package com.example.domain.di

import com.example.domain.repository.FirstRepository
import com.example.domain.usecase.GetTmpTimeUseCase
import com.example.domain.usecase.userInfo.UpdateUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetTmpTimeUseCase(repository: FirstRepository): GetTmpTimeUseCase {
        return GetTmpTimeUseCase(repository)
    }

}