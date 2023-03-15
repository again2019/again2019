package com.example.data.dataSource.dataStoreDataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreDataSourceImpl (
    private val preferencesDataStore: DataStore<Preferences>,
    private val protoDataStore: DataStore<UserSettings>,
    ) : DataStoreDataSource {

    // preferences data store

    private val RECENT_DATE = stringPreferencesKey("recent_date")

    override suspend fun setRecentDateFromPreferences(date: String?) {
        preferencesDataStore.edit { preferences ->
            preferences[RECENT_DATE] = date ?: ""
        }
    }

    override suspend fun getRecentDateFromPreferences(): Flow<String?> {
        return preferencesDataStore.data.map { preferences ->
            preferences[RECENT_DATE] ?: ""
        }
    }

    override suspend fun setRecentDateFromProto(date: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getRecentDateFromProto(): Flow<String?> {
        TODO("Not yet implemented")
    }

    // proto data source


}