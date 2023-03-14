package com.example.domain.repository

import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel

interface SavedTimeRepository {

    // 가장 처음에 추가되는 Entity
    suspend fun addMyInitSavedTimeMonthModel(savedTimeMonthModel: SavedTimeMonthModel)
    suspend fun addMyInitSavedTimeYearModel(savedTimeYearModel: SavedTimeYearModel)


    // savedTimeAboutRank

    suspend fun initSavedTimeAboutRankModel(savedTimeAboutRankModel: SavedTimeAboutRankModel)
    suspend fun addMySavedTimeAboutMonthRankModel(savedTimeAboutMonthRankModel: SavedTimeAboutRankModel)

    suspend fun addMySavedTimeAboutYearRankModel(savedTimeAboutYearRankModel: SavedTimeAboutRankModel)

    suspend fun getSavedTimeAboutMonthRankModel() : ArrayList<SavedTimeAboutRankModel>

    suspend fun getSavedTimeAboutYearRankModel() : ArrayList<SavedTimeAboutRankModel>

    suspend fun updateSavedTimeAboutMonthRankModel(yyyyMM: String, count: Double)

    suspend fun updateSavedTimeAboutYearRankModel(yyyy: String, count: Double)

    // mySavedTime

    suspend fun getMySavedTimeDayModel() : ArrayList<SavedTimeDayModel>
    suspend fun getMySavedTimeMonthModel() : ArrayList<SavedTimeMonthModel>

    suspend fun getMySavedTimeYearModel() : ArrayList<SavedTimeYearModel>

    // otherSavedTime

    suspend fun addMySavedTimeDayModel(savedTimeDayModel: SavedTimeDayModel)
    suspend fun addMySavedTimeMonthModel(savedTimeMonthModel: SavedTimeMonthModel)

    suspend fun addMySavedTimeYearModel(savedTimeYearModel: SavedTimeYearModel)

    suspend fun getOtherSavedTimeDayModel(destinationUid: String) : ArrayList<SavedTimeDayModel>

    suspend fun getOtherSavedTimeMonthModel(destinationUid: String) : ArrayList<SavedTimeMonthModel>

    suspend fun getOtherSavedTimeYearModel(destinationUid: String) : ArrayList<SavedTimeYearModel>


}