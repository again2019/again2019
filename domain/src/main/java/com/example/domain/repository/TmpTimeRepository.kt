package com.example.domain.repository

import com.example.domain.model.TmpTimeModel
import com.example.domain.util.DatabaseResult

interface TmpTimeRepository {

    /*
        DoingReceiver
    */

    suspend fun addTmpTimeModel(
        currentTime: String,
        tmpTimeEntity: TmpTimeModel,
        onResult: (DatabaseResult<String>) -> Unit
    )

    /*
        TmpTimeActivity
     */

    suspend fun deleteTmpTimeModel(
        startTime: String,
        onResult: (DatabaseResult<String>) -> Unit
    )


    /*
        FirstMainFragment
     */


    suspend fun getTmpTimeModelList(
        onResult: (DatabaseResult<List<TmpTimeModel>>) -> Unit
    )

    suspend fun updateTmpTimeDayModel(
        wakeUpTime1: String,
        wakeupTime2: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun updateTmpTimeMonthModel(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    )

    suspend fun updateTmpTimeYearModel(
        wakeUpTime: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    )

}