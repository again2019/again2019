package com.goingbacking.goingbacking.DI

import com.goingbacking.goingbacking.Repository.*
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepository
import com.goingbacking.goingbacking.Repository.Fifth.FifthRepositoryIF
import com.goingbacking.goingbacking.Repository.Login.LoginRepository
import com.goingbacking.goingbacking.Repository.Login.LoginRepositoryIF
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
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
        firebaseFirestore: FirebaseFirestore
    ) : InputRepositoryIF {
        return InputRepository(user, firebaseFirestore)
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
    fun provideTmpTimeRepository (
        user: FirebaseUser?,
        firebaseFirestore: FirebaseFirestore
    ) : TmpTimeRepositoryIF {
        return TmpTimeRepository(user, firebaseFirestore)
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(
        user: FirebaseUser?,
        firebaseFirestore: FirebaseFirestore
    ) : AlarmRepositoryIF {
        return AlarmRepository(user, firebaseFirestore)
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
        firebaseAuth: FirebaseAuth
    ) : FifthRepositoryIF {
        return FifthRepository(user, firebaseFirestore, firebaseAuth)
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