package com.goingbacking.goingbacking.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.domain.util.Constants
import com.example.domain.util.Constants.Companion.PAGE_SIZE
import com.example.domain.util.FBConstants
import com.example.domain.util.currentday
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFireStoreInstance(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthInstance() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserInstance() : FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    @Provides
    @Singleton
    fun provideFirebaseMessagingInstance() : FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideQueryRankingInfo(
//        firebaseFirestore: FirebaseFirestore
    ) : Query {
        return FirebaseFirestore.getInstance().collection(FBConstants.RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).orderBy(Constants.COUNT, Query.Direction.DESCENDING)
            .limit(PAGE_SIZE)
    }
}
