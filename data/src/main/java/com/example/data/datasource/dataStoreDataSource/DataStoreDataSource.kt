package com.example.data.datasource.dataStoreDataSource

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {

    // preferences data store

    // recent date

    suspend fun addRecentDateFromPreferences(date: String?)

    suspend fun getRecentDateFromPreferences() : Flow<String?>

    // today total time

    suspend fun addTodayTotalTimeFromPreferences(time: Int?)

    suspend fun getTodayTotalTimeFromPreferences() : Flow<Int?>

    // proto data source

    // recent date

    suspend fun addRecentDateFromProto(date: String?)

    suspend fun getRecentDateFromProto() : Flow<String?>

    // today total time

    suspend fun addTodayTotalTimeFromProto(time: Int?)

    suspend fun getTodayTotalTimeFromProto() : Flow<Int?>

}