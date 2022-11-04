package com.goingbacking.goingbacking.DI

import com.goingbacking.goingbacking.Repository.*
import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepository
import com.goingbacking.goingbacking.Repository.Alarm.AlarmRepositoryIF
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepository
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.Repository.First.FirstRepository
import com.goingbacking.goingbacking.Repository.First.FirstRepositoryIF
import com.goingbacking.goingbacking.Repository.Forth.ForthRepository
import com.goingbacking.goingbacking.Repository.Forth.ForthRepositoryIF
import com.goingbacking.goingbacking.Repository.Input.InputRepository
import com.goingbacking.goingbacking.Repository.Input.InputRepositoryIF
import com.goingbacking.goingbacking.Repository.Login.LoginRepository
import com.goingbacking.goingbacking.Repository.Login.LoginRepositoryIF
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
    fun provideInputRepository(
        user: FirebaseUser?,
        firebaseFirestore: FirebaseFirestore,
        firebaseMessage : FirebaseMessaging
    ) : InputRepositoryIF {
        return InputRepository(user, firebaseFirestore, firebaseMessage)
    }


    @Provides
    @Singleton
    fun provideMainRepository(
        user: FirebaseUser?,
        firebaseFirestore: FirebaseFirestore
    ) : MainRepositoryIF {
        return MainRepository(user, firebaseFirestore)
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
    fun provideForthRepository (
        firebaseFirestore: FirebaseFirestore,
        user: FirebaseUser?,
    ) : ForthRepositoryIF {
        return ForthRepository(user, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideFifthRepository (
        firebaseFirestore: FirebaseFirestore,
        user: FirebaseUser?,
        firebaseAuth: FirebaseAuth,
        firebaseMessage : FirebaseMessaging

    ) : FifthRepositoryIF {
        return FifthRepository(user, firebaseFirestore, firebaseAuth, firebaseMessage)
    }
    @Provides
    @Singleton
    fun provideRankRepository (
        firebaseFirestore: FirebaseFirestore,
        user: FirebaseUser?,
    ) : RankRepositoryIF {
        return RankRepository(user, firebaseFirestore)
    }


}