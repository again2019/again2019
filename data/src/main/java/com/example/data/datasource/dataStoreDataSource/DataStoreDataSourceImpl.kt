package com.example.data.datasource.dataStoreDataSource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.data.RecentDateSettings
import com.example.data.TodayTotalTimeSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreDataSourceImpl (
    private val preferencesDataStore: DataStore<Preferences>,
    private val recentDateProtoDataStore: DataStore<RecentDateSettings>,
    private val todayTotalTimeProtoDataStore: DataStore<TodayTotalTimeSettings>,
    ) : DataStoreDataSource {

    // preferences data store

    // recent date

    private val RECENT_DATE = stringPreferencesKey("RecentDate")

    override suspend fun addRecentDateFromPreferences(date: String?) {
        preferencesDataStore.edit { preferences ->
            preferences[RECENT_DATE] = date!!
        }
    }

    override suspend fun getRecentDateFromPreferences(): Flow<String?> {
        return preferencesDataStore.data.map { preferences ->
            preferences[RECENT_DATE] ?: ""
        }
    }

    // today total time

    private val TODAY_TOTAL_TIME = intPreferencesKey("TodayTotalTime")

    override suspend fun addTodayTotalTimeFromPreferences(time: Int?) {
        preferencesDataStore.edit { preferences ->
            preferences[TODAY_TOTAL_TIME] = time !!
        }
    }

    override suspend fun getTodayTotalTimeFromPreferences(): Flow<Int?> {
        return preferencesDataStore.data.map { preferences ->
            preferences[TODAY_TOTAL_TIME] ?: 0
        }
    }


    // proto data store

    override suspend fun addRecentDateFromProto(date: String?) {
        recentDateProtoDataStore.updateData { recentDateUserSettings ->
            recentDateUserSettings.toBuilder()
                .setDate(date)
                .build()
        }
    }

    override suspend fun getRecentDateFromProto(): Flow<String?> {
        return recentDateProtoDataStore.data.map { recentDateUserSettings ->
            recentDateUserSettings.date
        }
    }

    override suspend fun addTodayTotalTimeFromProto(time: Int?) {

        todayTotalTimeProtoDataStore.updateData { todayTotalTimeUserSettings ->
            todayTotalTimeUserSettings.toBuilder()
                .setTime(time!!)
                .build()
        }
    }

    override suspend fun getTodayTotalTimeFromProto(): Flow<Int?> {

        return todayTotalTimeProtoDataStore.data.map { todayTotalTimeUserSettings ->
            todayTotalTimeUserSettings.time
        }
    }


}