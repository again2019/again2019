package com.example.domain.repository

import com.example.domain.model.TmpTimeModel

interface TmpTimeRepository {

    // TmpTime
    suspend fun addTmpTimeModel(currentTime: String, tmpTimeEntity: TmpTimeModel)

    suspend fun getTmpTimeModel() : ArrayList<TmpTimeModel>

    suspend fun deleteTmpTimeModel(startTime: String)

    suspend fun updateTmpTimeDayModel(wakeUpTime1: String, wakeupTime2: String, count: Double)

    suspend fun updateTmpTimeMonthModel(wakeUpTime1: String, wakeUpTime2: String, count: Double)

    suspend fun updateTmpTimeYearModel(wakeUpTime: String, count: Double)

}