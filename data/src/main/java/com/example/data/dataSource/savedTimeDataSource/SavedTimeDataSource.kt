package com.example.data.dataSource.savedTimeDataSource

import com.example.data.entity.*
import com.google.firebase.firestore.FieldValue

interface SavedTimeDataSource {

    // savedTimeAboutRank

    suspend fun getSavedTimeAboutMonthRankEntity() : ArrayList<SavedTimeAboutRankEntity>

    suspend fun getSavedTimeAboutYearRankEntity() : ArrayList<SavedTimeAboutRankEntity>

    suspend fun updateSavedTimeAboutMonthRankEntity(yyyyMM: String, count: Double)

    suspend fun updateSavedTimeAboutYearRankEntity(yyyy: String, count: Double)

    // mySavedTime

    suspend fun getMySavedTimeDayEntity() : ArrayList<SavedTimeDayEntity>

    suspend fun getMySavedTimeMonthEntity() : ArrayList<SavedTimeMonthEntity>

    suspend fun getMySavedTimeYearEntity() : ArrayList<SavedTimeYearEntity>

    // otherSavedTime

    suspend fun getOtherSavedTimeDayEntity(destinationUid: String) : ArrayList<SavedTimeDayEntity>

    suspend fun getOtherSavedTimeMonthEntity(destinationUid: String) : ArrayList<SavedTimeMonthEntity>

    suspend fun getOtherSavedTimeYearEntity(destinationUid: String) : ArrayList<SavedTimeYearEntity>

}