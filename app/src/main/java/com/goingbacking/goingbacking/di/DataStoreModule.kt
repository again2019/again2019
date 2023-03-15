package com.goingbacking.goingbacking.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.goingbacking.goingbacking.SettingsSerializer
import com.goingbacking.goingbacking.UserSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {


    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "com.goingbacking.goingbacking.userPreferences"
    )

    @Provides
    @Singleton
    fun provideRecentDateDataStore(
        @ApplicationContext applicationContext : Context
    ) : DataStore<Preferences> {
        return applicationContext.userPreferencesDataStore
    }


    private val Context.userProtoDataStore: DataStore<UserSettings> by dataStore(
        fileName = "protoDataStore.pb",
        serializer = SettingsSerializer
    )
}






