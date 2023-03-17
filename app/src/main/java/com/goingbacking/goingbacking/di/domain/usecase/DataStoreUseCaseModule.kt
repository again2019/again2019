package com.goingbacking.goingbacking.di.domain.usecase

import com.example.domain.repository.AccountRepository
import com.example.domain.repository.DataStoreRepository
import com.example.domain.usecase.dataStore.recentDate.AddRecentDateFromPreferencesUseCase
import com.example.domain.usecase.dataStore.recentDate.AddRecentDateFromProtoUseCase
import com.example.domain.usecase.dataStore.recentDate.GetRecentDateFromPreferencesUseCase
import com.example.domain.usecase.dataStore.recentDate.GetRecentDateFromProtoUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.AddTodayTotalTimeFromPreferencesUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.AddTodayTotalTimeFromProtoUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.GetTodayTotalTimeFromPreferencesUseCase
import com.example.domain.usecase.dataStore.todayTotalTime.GetTodayTotalTimeFromProtoUseCase
import com.example.domain.usecase.myAccount.FindEmailPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object DataStoreUseCaseModule {

    @Provides
    fun provideAddRecentDateFromPreferencesUseCase(
        dataStoreRepository: DataStoreRepository
    ) : AddRecentDateFromPreferencesUseCase {
        return AddRecentDateFromPreferencesUseCase(dataStoreRepository)
    }

    @Provides
    fun provideAddRecentDateFromProtoUseCase(
        dataStoreRepository: DataStoreRepository
    ) : AddRecentDateFromProtoUseCase {
        return AddRecentDateFromProtoUseCase(dataStoreRepository)
    }

    @Provides
    fun provideGetRecentDateFromPreferencesUseCase(
        dataStoreRepository: DataStoreRepository
    ) : GetRecentDateFromPreferencesUseCase {
        return GetRecentDateFromPreferencesUseCase(dataStoreRepository)
    }

    @Provides
    fun provideGetRecentDateFromProtoUseCase(
        dataStoreRepository: DataStoreRepository
    ) : GetRecentDateFromProtoUseCase {
        return GetRecentDateFromProtoUseCase(dataStoreRepository)
    }


    @Provides
    fun provideAddTodayTotalTimeFromPreferencesUseCase(
        dataStoreRepository: DataStoreRepository
    ) : AddTodayTotalTimeFromPreferencesUseCase {
        return AddTodayTotalTimeFromPreferencesUseCase(dataStoreRepository)
    }

    @Provides
    fun provideAddTodayTotalTimeFromProtoUseCase(
        dataStoreRepository: DataStoreRepository
    ) : AddTodayTotalTimeFromProtoUseCase {
        return AddTodayTotalTimeFromProtoUseCase(dataStoreRepository)
    }

    @Provides
    fun provideGetTodayTotalTimeFromPreferencesUseCase(
        dataStoreRepository: DataStoreRepository
    ) : GetTodayTotalTimeFromPreferencesUseCase {
        return GetTodayTotalTimeFromPreferencesUseCase(dataStoreRepository)
    }

    @Provides
    fun provideGetTodayTotalTimeFromProtoUseCase(
        dataStoreRepository: DataStoreRepository
    ) : GetTodayTotalTimeFromProtoUseCase {
        return GetTodayTotalTimeFromProtoUseCase(dataStoreRepository)
    }
}