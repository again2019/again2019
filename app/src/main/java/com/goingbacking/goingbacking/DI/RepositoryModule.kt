package com.goingbacking.goingbacking.DI

import com.goingbacking.goingbacking.Repository.*
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

}