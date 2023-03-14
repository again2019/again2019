package com.goingbacking.goingbacking.di

import com.goingbacking.goingbacking.repository.alarm.AlarmRepository
import com.goingbacking.goingbacking.repository.alarm.AlarmRepositoryIF
import com.goingbacking.goingbacking.repository.fifth.FifthRepository
import com.goingbacking.goingbacking.repository.fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.repository.first.FirstRepository
import com.goingbacking.goingbacking.repository.first.FirstRepositoryIF
import com.goingbacking.goingbacking.repository.forth.ForthRepository
import com.goingbacking.goingbacking.repository.forth.ForthRepositoryIF
import com.goingbacking.goingbacking.repository.forth.RankRepository
import com.goingbacking.goingbacking.repository.forth.RankRepositoryIF
import com.goingbacking.goingbacking.repository.input.InputRepository
import com.goingbacking.goingbacking.repository.input.InputRepositoryIF
import com.goingbacking.goingbacking.repository.login.LoginRepository
import com.goingbacking.goingbacking.repository.login.LoginRepositoryIF
import com.goingbacking.goingbacking.repository.second.SecondRepository
import com.goingbacking.goingbacking.repository.second.SecondRepositoryIF
import com.goingbacking.goingbacking.repository.third.ThirdRepository
import com.goingbacking.goingbacking.repository.third.ThirdRepositoryIF
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.Constants.Companion.PAGE_SIZE
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
//        firebaseFirestore: FirebaseFirestore,
//        firebaseAuth: FirebaseAuth
    ) : LoginRepositoryIF {
//        return LoginRepository(firebaseFirestore, firebaseAuth)
        return LoginRepository()
    }

    @Provides
    @Singleton
    fun provideInputRepository(
//        user: FirebaseUser?,
//        firebaseFirestore: FirebaseFirestore,
//        firebaseMessage : FirebaseMessaging
    ) : InputRepositoryIF {
//        return InputRepository(user, firebaseFirestore, firebaseMessage)
        return InputRepository()
    }

    @Provides
    @Singleton
    fun provideFirstRepository (
//        user: FirebaseUser?,
//        firebaseFirestore: FirebaseFirestore
    ) : FirstRepositoryIF {
//        return FirstRepository(user, firebaseFirestore)
        return FirstRepository()
    }

    @Provides
    @Singleton
    fun provideSecondRepository(
//        user: FirebaseUser?,
//        firebaseFirestore: FirebaseFirestore
    ) : SecondRepositoryIF {
//        return SecondRepository(user, firebaseFirestore)
        return SecondRepository()
    }

    @Provides
    @Singleton
    fun provideThirdRepository (
//        firebaseFirestore: FirebaseFirestore,
//        firebaseAuth: FirebaseAuth
    ) : ThirdRepositoryIF {
//        return ThirdRepository(firebaseFirestore, firebaseAuth)
        return ThirdRepository()
    }

    @Provides
    @Singleton
    fun provideForthRepository (
//        firebaseFirestore: FirebaseFirestore,
//        user: FirebaseUser?,
    ) : ForthRepositoryIF {
//        return ForthRepository(user, firebaseFirestore, notificationAPI)
        return ForthRepository()
    }

    @Provides
    @Singleton
    fun provideQueryRankingInfo(
//        firebaseFirestore: FirebaseFirestore
    ) : Query {
        return FirebaseFirestore.getInstance().collection(FBConstants.RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).orderBy(Constants.COUNT, Query.Direction.DESCENDING)
            .limit(PAGE_SIZE)
    }


    @Provides
    @Singleton
    fun provideRankRepository (
//        firebaseFirestore: FirebaseFirestore,
//        user: FirebaseUser?,
    ) : RankRepositoryIF {
//        return RankRepository(user, firebaseFirestore, notificationAPI)
        return RankRepository()
    }

    @Provides
    @Singleton
    fun provideFifthRepository (
//        firebaseFirestore: FirebaseFirestore,
//        user: FirebaseUser?,
//        firebaseAuth: FirebaseAuth,
    ) : FifthRepositoryIF {
//        return FifthRepository(user, firebaseFirestore, firebaseAuth)
        return FifthRepository()
    }















}