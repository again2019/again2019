package com.example.data.datasource.tmptimedatasource

import com.example.data.entity.TmpTimeEntity
import com.example.domain.util.DatabaseResult
interface TmpTimeDataSource {

     /*
          DoingReceiver
      */

     suspend fun addTmpTimeEntity(
          currentTime: String,
          tmpTimeEntity: TmpTimeEntity,
          onResult: (DatabaseResult<String>) -> Unit
     )

     /*
          TmpTimeActivity
      */

     suspend fun deleteTmpTimeEntity(
          startTime: String,
          onResult: (DatabaseResult<String>) -> Unit
     )



     /*
          FirstMainFragment
      */

     suspend fun getTmpTimeEntityList(
          onResult: (DatabaseResult<List<TmpTimeEntity>>) -> Unit
     )



     suspend fun updateTmpTimeDayEntity(
          wakeUpTime1: String,
          wakeupTime2: String,
          count: Double,
          onResult: (DatabaseResult<String>) -> Unit
     )

     suspend fun updateTmpTimeMonthEntity(
          wakeUpTime1: String,
          wakeUpTime2: String,
          count: Double,
          onResult: (DatabaseResult<String>) -> Unit
     )

     suspend fun updateTmpTimeYearEntity(
          wakeUpTime: String,
          count: Double,
          onResult: (DatabaseResult<String>) -> Unit
     )

}