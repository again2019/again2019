package com.example.domain.repository

import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel

interface SavedTimeRepository {
    suspend fun getSavedTimeDayModel() : ArrayList<SavedTimeDayModel>

    suspend fun getSavedTimeMonthModel() : ArrayList<SavedTimeMonthModel>

    suspend fun getSavedTimeYearModel() : ArrayList<SavedTimeYearModel>

}