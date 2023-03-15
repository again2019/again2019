package com.goingbacking.goingbacking.di


import com.example.data.api.NotificationAPI
import com.example.data.dataSource.accountDataSource.AccountDataSource
import com.example.data.dataSource.accountDataSource.AccountDataSourceImpl
import com.example.data.dataSource.notificationDataSource.NotificationDataSource
import com.example.data.dataSource.notificationDataSource.NotificationDataSourceImpl
import com.example.data.dataSource.savedTimeDataSource.SavedTimeDataSource
import com.example.data.dataSource.savedTimeDataSource.SavedTimeDataSourceImpl
import com.example.data.dataSource.scheduleAndDateDataSource.ScheduleAndDateDataSource
import com.example.data.dataSource.scheduleAndDateDataSource.ScheduleAndDateDataSourceImpl
import com.example.data.dataSource.tmpTimeDataSource.TmpTimeDataSource
import com.example.data.dataSource.tmpTimeDataSource.TmpTimeDataSourceImpl
import com.example.data.dataSource.userInfoDataSource.UserInfoDataSource
import com.example.data.dataSource.userInfoDataSource.UserInfoDataSourceImpl
import com.example.data.dataSource.whatToDoDataSource.WhatToDoDataSource
import com.example.data.dataSource.whatToDoDataSource.WhatToDoDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
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
    fun provideAccountDataSource(
        firebaseUser: FirebaseUser?,
        firebaseAuth: FirebaseAuth,
    ) : AccountDataSource {
        return AccountDataSourceImpl(firebaseUser, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideSavedTimeDataSource(
        firebaseFirestore: FirebaseFirestore,
        firebaseUser: FirebaseUser?,
    ) : SavedTimeDataSource {
        return SavedTimeDataSourceImpl(firebaseFirestore, firebaseUser!!)
    }

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
        firebaseMessaging: FirebaseMessaging,
    ) : UserInfoDataSource {
        return UserInfoDataSourceImpl(firebaseFirestore, firebaseUser!!, firebaseMessaging)
    }

    @Provides
    @Singleton
    fun provideWhatToDoDataSource(
        firebaseFirestore: FirebaseFirestore,
        firebaseUser: FirebaseUser?,
    ) : WhatToDoDataSource {
        return WhatToDoDataSourceImpl(firebaseFirestore, firebaseUser!!)
    }

    @Provides
    @Singleton
    fun provideScheduleAndDateDataSource(
        firebaseFirestore: FirebaseFirestore,
        firebaseUser: FirebaseUser?,
    ) : ScheduleAndDateDataSource {
        return ScheduleAndDateDataSourceImpl(firebaseFirestore, firebaseUser!!)
    }

    @Provides
    @Singleton
    fun provideNotificationDataSource(
        api : NotificationAPI
    ) : NotificationDataSource {
        return NotificationDataSourceImpl(api)
    }


}