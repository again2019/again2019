package com.example.data.di


import com.example.domain.repository.FirstRepository
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
    abstract fun bindsFirstRepository(repositoryImpl: FirstRepositoryImpl) : FirstRepository

}