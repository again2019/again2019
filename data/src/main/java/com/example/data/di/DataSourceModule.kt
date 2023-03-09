package com.example.data.di

import com.example.data.dataSource.TmpTimeDataSource
import com.example.data.dataSource.TmpTimeDataSourceImpl
import com.example.data.dataSource.UserInfoDataSource
import com.example.data.dataSource.UserInfoDataSourceImpl
import com.goingbacking.goingbacking.Repository.First.FirstRepositoryImpl
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
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


//    @Provides
//    @Singleton
//    fun provideFirstRepository(
//        tmpTmpTimeDataSource: TmpTimeDataSource
//    ) : FirstRepositoryImpl {
//        return FirstRepositoryImpl(tmpTmpTimeDataSource)
//    }

}