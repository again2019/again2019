package com.example.data.di

import com.example.data.dataSource.tmpTimeDataSource.TmpTimeDataSource
import com.example.data.dataSource.tmpTimeDataSource.TmpTimeDataSourceImpl
import com.example.data.dataSource.userInfoDataSource.UserInfoDataSource
import com.example.data.dataSource.userInfoDataSource.UserInfoDataSourceImpl
import com.example.data.dataSource.whatToDoDataSource.WhatToDoDataSource
import com.example.data.dataSource.whatToDoDataSource.WhatToDoDataSourceImpl
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {


    @Provides
    @Singleton
    fun provideTmpTimeDataSource(
        firebaseFirestore: FirebaseFirestore,
        firebaseUser: FirebaseUser?,
    ) : TmpTimeDataSource {
        return TmpTimeDataSourceImpl(firebaseFirestore, firebaseUser!!)
    }

    @Provides
    @Singleton
    fun provideUserInfoDataSource(
        firebaseFirestore: FirebaseFirestore,
        firebaseUser: FirebaseUser?,
    ) : UserInfoDataSource {
        return UserInfoDataSourceImpl(firebaseFirestore, firebaseUser!!)
    }

    @Provides
    @Singleton
    fun provideWhatToDoDataSource(
        firebaseFirestore: FirebaseFirestore,
        firebaseUser: FirebaseUser?,
    ) : WhatToDoDataSource {
        return WhatToDoDataSourceImpl(firebaseFirestore, firebaseUser!!)
    }

//    @Provides
//    @Singleton
//    fun provideFirstRepository(
//        tmpTmpTimeDataSource: TmpTimeDataSource
//    ) : FirstRepositoryImpl {
//        return FirstRepositoryImpl(tmpTmpTimeDataSource)
//    }

}