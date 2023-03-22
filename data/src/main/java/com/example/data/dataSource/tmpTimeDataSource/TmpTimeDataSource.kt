package com.example.data.dataSource.tmpTimeDataSource

import com.example.data.entity.TmpTimeEntity
import com.example.domain.util.Response

interface TmpTimeDataSource {

     // TmpTime

     suspend fun addTmpTimeEntity(currentTime: String, tmpTimeEntity: TmpTimeEntity)

     suspend fun getTmpTimeEntity(result: (Response<List<TmpTimeEntity>>) -> Unit)

//     suspend fun getTmpTimeEntity(onResult: (String) -> Unit) : ArrayList<TmpTimeEntity>

     suspend fun deleteTmpTimeEntity(startTime: String)

     suspend fun updateTmpTimeDayEntity(wakeUpTime1: String, wakeupTime2: String, count: Double)

     suspend fun updateTmpTimeMonthEntity(wakeUpTime1: String, wakeUpTime2: String, count: Double)

     suspend fun updateTmpTimeYearEntity(wakeUpTime: String, count: Double)

}