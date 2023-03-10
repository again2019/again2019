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

    // savedTime

    suspend fun getSavedTimeDayModel() : ArrayList<SavedTimeDayModel>
    suspend fun getSavedTimeMonthModel() : ArrayList<SavedTimeMonthModel>

    suspend fun getSavedTimeYearModel() : ArrayList<SavedTimeYearModel>

}