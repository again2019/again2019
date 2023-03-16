package com.example.data.repositoryImpl

import android.util.Log
import com.example.data.dataSource.dataStoreDataSource.DataStoreDataSource
import com.example.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource
): DataStoreRepository {

    // preferences data store

    // recent date

    override suspend fun addRecentDateFromPreferences(date: String?) {
        dataStoreDataSource.addRecentDateFromPreferences(date)
    }

    override suspend fun getRecentDateFromPreferences(): Flow<String?> {
        return dataStoreDataSource.getRecentDateFromPreferences()
    }

    // today total time

    override suspend fun addTodayTotalTimeFromPreferences(time: Int?) {
        dataStoreDataSource.addTodayTotalTimeFromPreferences(time)
    }

    override suspend fun getTodayTotalTimeFromPreferences(): Flow<Int?> {
        return dataStoreDataSource.getTodayTotalTimeFromPreferences()
    }

    // proto data store

    // recent date

    override suspend fun addRecentDateFromProto(date: String?) {
        dataStoreDataSource.addRecentDateFromProto(date)
    }

    override suspend fun getRecentDateFromProto(): Flow<String?> {
        return dataStoreDataSource.getRecentDateFromProto()
    }

    // today total time

    override suspend fun addTodayTotalTimeFromProto(time: Int?) {
        dataStoreDataSource.addTodayTotalTimeFromProto(time)
    }

    override suspend fun getTodayTotalTimeFromProto(): Flow<Int?> {
        return dataStoreDataSource.getTodayTotalTimeFromProto()
    }
}