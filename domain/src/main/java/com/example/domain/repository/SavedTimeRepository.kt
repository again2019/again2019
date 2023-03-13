package com.example.domain.repository

import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel

interface SavedTimeRepository {

    // savedTimeAboutRank

    suspend fun getSavedTimeAboutMonthRankModel() : ArrayList<SavedTimeAboutRankModel>

    suspend fun getSavedTimeAboutYearRankModel() : ArrayList<SavedTimeAboutRankModel>

    suspend fun updateSavedTimeAboutMonthRankModel(yyyyMM: String, count: Double)

    suspend fun updateSavedTimeAboutYearRankModel(yyyy: String, count: Double)

    // mySavedTime

    suspend fun getMySavedTimeDayModel() : ArrayList<SavedTimeDayModel>
    suspend fun getMySavedTimeMonthModel() : ArrayList<SavedTimeMonthModel>

    suspend fun getMySavedTimeYearModel() : ArrayList<SavedTimeYearModel>

    // otherSavedTime

    suspend fun getOtherSavedTimeDayModel(destinationUid: String) : ArrayList<SavedTimeDayModel>

    suspend fun getOtherSavedTimeMonthModel(destinationUid: String) : ArrayList<SavedTimeMonthModel>

    suspend fun getOtherSavedTimeYearModel(destinationUid: String) : ArrayList<SavedTimeYearModel>


}