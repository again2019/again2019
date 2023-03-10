package com.example.data.di


import com.example.data.repositoryImpl.SavedTimeRepositoryImpl
import com.example.data.repositoryImpl.UserInfoRepositoryImpl
import com.example.data.repositoryImpl.WhatToDoRepositoryImpl
import com.example.domain.repository.TmpTimeRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.repository.WhatToDoRepository
import com.example.data.repositoryImpl.TmpTimeRepositoryImpl
import com.example.domain.repository.SavedTimeRepository
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




}