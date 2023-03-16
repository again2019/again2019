package com.example.domain.repository

import kotlinx.coroutines.flow.Flow


interface DataStoreRepository {


    // preferences data store

    // recent date

    suspend fun addRecentDateFromPreferences(date: String?)

    suspend fun getRecentDateFromPreferences(): Flow<String?>

    suspend fun addTodayTotalTimeFromPreferences(time: Int?)

    suspend fun getTodayTotalTimeFromPreferences() : Flow<Int?>

    // proto data store

    // today total time

    suspend fun addRecentDateFromProto(date: String?)

    suspend fun getRecentDateFromProto(): Flow<String?>

    suspend fun addTodayTotalTimeFromProto(time: Int?)

    suspend fun getTodayTotalTimeFromProto() : Flow<Int?>

}
