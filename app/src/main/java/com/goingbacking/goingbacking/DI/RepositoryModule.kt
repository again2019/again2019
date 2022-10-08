package com.goingbacking.goingbacking.DI

import com.goingbacking.goingbacking.Repository.InputRepository
import com.goingbacking.goingbacking.Repository.InputRepositoryIF
import com.google.firebase.auth.FirebaseAuth
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
        auth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ) : InputRepositoryIF {
        return InputRepository(auth, firebaseFirestore)
    }




}