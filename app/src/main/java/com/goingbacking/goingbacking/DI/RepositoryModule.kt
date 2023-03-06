package com.goingbacking.goingbacking.DI

import com.goingbacking.goingbacking.FCM.NotificationAPI
import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepository
import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepositoryIF
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepository
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.Repository.First.FirstRepository
import com.goingbacking.goingbacking.Repository.First.FirstRepositoryIF
import com.goingbacking.goingbacking.Repository.Forth.ForthRepository
import com.goingbacking.goingbacking.Repository.Forth.ForthRepositoryIF
import com.goingbacking.goingbacking.Repository.Forth.RankRepository
import com.goingbacking.goingbacking.Repository.Forth.RankRepositoryIF
import com.goingbacking.goingbacking.Repository.Input.InputRepository
import com.goingbacking.goingbacking.Repository.Input.InputRepositoryIF
import com.goingbacking.goingbacking.Repository.Login.LoginRepository
import com.goingbacking.goingbacking.Repository.Login.LoginRepositoryIF
import com.goingbacking.goingbacking.Repository.Second.SecondRepository
import com.goingbacking.goingbacking.Repository.Second.SecondRepositoryIF
import com.goingbacking.goingbacking.Repository.Third.ThirdRepository
import com.goingbacking.goingbacking.Repository.Third.ThirdRepositoryIF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAlarmRepository() : AlarmRepositoryIF {
        return AlarmRepository()
    }

    @Provides
    @Singleton
    fun provideLoginRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ) : LoginRepositoryIF {
        return LoginRepository(firebaseAuth, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideInputRepository(
        user: FirebaseUser?,
        firebaseFirestore: FirebaseFirestore,
        firebaseMessage : FirebaseMessaging
    ) : InputRepositoryIF {
        return InputRepository(user, firebaseFirestore, firebaseMessage)
    }

    @Provides
    @Singleton
    fun provideFirstRepository (
        user: FirebaseUser?,
        firebaseFirestore: FirebaseFirestore
    ) : FirstRepositoryIF {
        return FirstRepository(user, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideSecondRepository(
        user: FirebaseUser?,
        firebaseFirestore: FirebaseFirestore
    ) : SecondRepositoryIF {
        return SecondRepository(user, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideThirdRepository (
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ) : ThirdRepositoryIF {
        return ThirdRepository(firebaseFirestore, firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideForthRepository (
        firebaseFirestore: FirebaseFirestore,
        user: FirebaseUser?,
        notificationAPI: NotificationAPI
    ) : ForthRepositoryIF {
        return ForthRepository(user, firebaseFirestore, notificationAPI)
    }

    @Provides
    @Singleton
    fun provideRankRepository (
        firebaseFirestore: FirebaseFirestore,
        user: FirebaseUser?,
        notificationAPI: NotificationAPI
    ) : RankRepositoryIF {
        return RankRepository(user, firebaseFirestore, notificationAPI)
    }

    @Provides
    @Singleton
    fun provideFifthRepository (
        firebaseFirestore: FirebaseFirestore,
        user: FirebaseUser?,
        firebaseAuth: FirebaseAuth,
    ) : FifthRepositoryIF {
        return FifthRepository(user, firebaseFirestore, firebaseAuth)
    }















}