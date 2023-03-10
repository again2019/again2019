package com.example.data.dataSource.savedTimeDataSource

import com.example.data.entity.SavedTimeDayEntity
import com.example.data.entity.SavedTimeMonthEntity
import com.example.data.entity.SavedTimeYearEntity
import com.example.data.entity.TmpTimeEntity

interface SavedTimeDataSource {
    suspend fun getSavedTimeDayEntity() : ArrayList<SavedTimeDayEntity>

    suspend fun getSavedTimeMonthEntity() : ArrayList<SavedTimeMonthEntity>

    suspend fun getSavedTimeYearEntity() : ArrayList<SavedTimeYearEntity>
}