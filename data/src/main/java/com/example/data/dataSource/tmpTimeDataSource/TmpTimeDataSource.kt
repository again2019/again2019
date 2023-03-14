package com.example.data.dataSource.tmpTimeDataSource

import com.example.data.entity.TmpTimeEntity

interface TmpTimeDataSource {

     // TmpTime

     suspend fun addTmpTimeEntity(currentTime: String, tmpTimeEntity: TmpTimeEntity)
     suspend fun getTmpTimeEntity() : ArrayList<TmpTimeEntity>

     suspend fun deleteTmpTimeEntity(startTime: String)

     suspend fun updateTmpTimeDayEntity(wakeUpTime1: String, wakeupTime2: String, count: Double)

     suspend fun updateTmpTimeMonthEntity(wakeUpTime1: String, wakeUpTime2: String, count: Double)

     suspend fun updateTmpTimeYearEntity(wakeUpTime: String, count: Double)

}