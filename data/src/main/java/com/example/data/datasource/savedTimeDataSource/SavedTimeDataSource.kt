package com.example.data.datasource.savedTimeDataSource

import com.example.data.entity.*
import com.example.domain.util.DatabaseResult

interface SavedTimeDataSource {


    /*
        가장 처음에 추가되는 Entity
     */

    suspend fun addMyInitSavedTimeMonthEntity(
        savedTimeMonthEntity: SavedTimeMonthEntity,
        onResult : (DatabaseResult<String>) -> Unit
    )

    suspend fun addMyInitSavedTimeYearEntity(
        savedTimeYearEntity: SavedTimeYearEntity,
        onResult: (DatabaseResult<String>) -> Unit
    )

    /*
        savedTimeAboutRank
     */

    suspend fun initSavedTimeAboutRankEntity(
        savedTimeAboutRankEntity: SavedTimeAboutRankEntity,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun addMySavedTimeAboutMonthRankEntity(
        savedTimeAboutMonthRankEntity: SavedTimeAboutRankEntity,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun addMySavedTimeAboutYearRankEntity(
        savedTimeAboutMonthRankEntity: SavedTimeAboutRankEntity,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun getSavedTimeAboutMonthRankEntityList(
        onResult: (DatabaseResult<List<SavedTimeAboutRankEntity>>) -> Unit
    )

    suspend fun getSavedTimeAboutYearRankEntityList(
        onResult: (DatabaseResult<List<SavedTimeAboutRankEntity>>) -> Unit
    )

    suspend fun updateSavedTimeAboutMonthRankEntity(
        yyyyMM: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun updateSavedTimeAboutYearRankEntity(
        yyyy: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    )

    /*
        mySavedTime
     */

    suspend fun addMySavedTimeDayEntity(
        savedTimeDayEntity: SavedTimeDayEntity,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun addMySavedTimeMonthEntity(
        savedTimeMonthEntity: SavedTimeMonthEntity,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun addMySavedTimeYearEntity(
        savedTimeYearEntity: SavedTimeYearEntity,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun getMySavedTimeDayEntity(
        onResult: (DatabaseResult<List<SavedTimeDayEntity>>) -> Unit
    )

    suspend fun getMySavedTimeMonthEntity(
        onResult: (DatabaseResult<List<SavedTimeMonthEntity>>) -> Unit
    )

    suspend fun getMySavedTimeYearEntity(
        onResult: (DatabaseResult<List<SavedTimeYearEntity>>) -> Unit
    )

    /*
        otherSavedTime
     */

    suspend fun getOtherSavedTimeDayEntity(
        destinationUid: String,
        onResult: (DatabaseResult<List<SavedTimeDayEntity>>) -> Unit
    )

    suspend fun getOtherSavedTimeMonthEntity(
        destinationUid: String,
        onResult: (DatabaseResult<List<SavedTimeMonthEntity>>) -> Unit
    )

    suspend fun getOtherSavedTimeYearEntity(
        destinationUid: String,
        onResult: (DatabaseResult<List<SavedTimeYearEntity>>) -> Unit
    )
}