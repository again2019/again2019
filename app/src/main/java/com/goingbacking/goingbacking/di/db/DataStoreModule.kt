package com.goingbacking.goingbacking.di.db

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.RecentDateSettings
import com.example.data.RecentDateSettingsSerializer
import com.example.data.TodayTotalTimeSettings
import com.example.data.TodayTotalTimeSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {


    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "com.goingbacking.goingbacking.userPreferences"
    )

    @Provides
    @Singleton
    fun provideRecentPreferencesDateDataStore(
        @ApplicationContext applicationContext : Context
    ) : DataStore<Preferences> {
        return applicationContext.userPreferencesDataStore
    }


    private val Context.recentDateProtoDataStore: DataStore<RecentDateSettings> by dataStore(
        fileName = "recentDateProtoDataStore.pb",
        serializer = RecentDateSettingsSerializer
    )

    @Provides
    @Singleton
    fun provideRecentProtoDateDataStore(
        @ApplicationContext applicationContext : Context
    ) : DataStore<RecentDateSettings> {
        return applicationContext.recentDateProtoDataStore
    }




    private val Context.todayTotalTimeProtoDataStore: DataStore<TodayTotalTimeSettings> by dataStore(
        fileName = "todayTotalTimeProtoDataStore.pb",
        serializer = TodayTotalTimeSettingsSerializer
    )

    @Provides
    @Singleton
    fun provideTodayTotalTimeProtoDateDataStore(
        @ApplicationContext applicationContext : Context
    ) : DataStore<TodayTotalTimeSettings> {
        return applicationContext.todayTotalTimeProtoDataStore
    }
}






