package com.example.data.dataSource.dataStoreDataSource

import kotlinx.coroutines.flow.Flow

interface DataStoreDataSource {

    // preferences data store

    suspend fun setRecentDateFromPreferences(date: String?)

    suspend fun getRecentDateFromPreferences() : Flow<String?>

    // proto data source

    suspend fun setRecentDateFromProto(date: String?)

    suspend fun getRecentDateFromProto() : Flow<String?>
}