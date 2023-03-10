package com.example.data.dataSource.savedTimeDataSource

import com.example.data.entity.*
import com.google.firebase.firestore.FieldValue

interface SavedTimeDataSource {

    // savedTimeAboutRank

    suspend fun getSavedTimeAboutMonthRankEntity() : ArrayList<SavedTimeAboutRankEntity>

    suspend fun getSavedTimeAboutYearRankEntity() : ArrayList<SavedTimeAboutRankEntity>

    suspend fun updateSavedTimeAboutMonthRankEntity(yyyyMM: String, count: Double)

    suspend fun updateSavedTimeAboutYearRankEntity(yyyy: String, count: Double)

    // savedTime

    suspend fun getSavedTimeDayEntity() : ArrayList<SavedTimeDayEntity>

    suspend fun getSavedTimeMonthEntity() : ArrayList<SavedTimeMonthEntity>

    suspend fun getSavedTimeYearEntity() : ArrayList<SavedTimeYearEntity>
}