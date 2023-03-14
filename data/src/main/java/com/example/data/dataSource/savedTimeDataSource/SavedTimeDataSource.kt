package com.example.data.dataSource.savedTimeDataSource

import com.example.data.entity.*
import com.google.firebase.firestore.FieldValue

interface SavedTimeDataSource {


    // 가장 처음에 추가되는 Entity
    suspend fun addMyInitSavedTimeMonthEntity(savedTimeMonthEntity: SavedTimeMonthEntity)

    suspend fun addMyInitSavedTimeYearEntity(savedTimeYearEntity: SavedTimeYearEntity)

    // savedTimeAboutRank

    suspend fun initSavedTimeAboutRankEntity(savedTimeAboutRankEntity: SavedTimeAboutRankEntity)

    suspend fun addMySavedTimeAboutMonthRankEntity(savedTimeAboutMonthRankEntity: SavedTimeAboutRankEntity)

    suspend fun addMySavedTimeAboutYearRankEntity(savedTimeAboutMonthRankEntity: SavedTimeAboutRankEntity)

    suspend fun getSavedTimeAboutMonthRankEntity() : ArrayList<SavedTimeAboutRankEntity>

    suspend fun getSavedTimeAboutYearRankEntity() : ArrayList<SavedTimeAboutRankEntity>

    suspend fun updateSavedTimeAboutMonthRankEntity(yyyyMM: String, count: Double)

    suspend fun updateSavedTimeAboutYearRankEntity(yyyy: String, count: Double)

    // mySavedTime

    suspend fun addMySavedTimeDayEntity(savedTimeDayEntity: SavedTimeDayEntity)

    suspend fun addMySavedTimeMonthEntity(savedTimeMonthEntity: SavedTimeMonthEntity)

    suspend fun addMySavedTimeYearEntity(savedTimeYearEntity: SavedTimeYearEntity)

    suspend fun getMySavedTimeDayEntity() : ArrayList<SavedTimeDayEntity>

    suspend fun getMySavedTimeMonthEntity() : ArrayList<SavedTimeMonthEntity>

    suspend fun getMySavedTimeYearEntity() : ArrayList<SavedTimeYearEntity>

    // otherSavedTime

    suspend fun getOtherSavedTimeDayEntity(destinationUid: String) : ArrayList<SavedTimeDayEntity>

    suspend fun getOtherSavedTimeMonthEntity(destinationUid: String) : ArrayList<SavedTimeMonthEntity>

    suspend fun getOtherSavedTimeYearEntity(destinationUid: String) : ArrayList<SavedTimeYearEntity>

}