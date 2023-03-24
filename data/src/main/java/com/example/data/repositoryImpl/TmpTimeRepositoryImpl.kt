package com.example.data.repositoryImpl

import com.example.data.datasource.tmptimedatasource.TmpTimeDataSource
import com.example.data.mapper.TmpTimeMapper
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository
import javax.inject.Inject
import com.example.domain.util.DatabaseResult

class TmpTimeRepositoryImpl @Inject constructor (
    private val tmpTimeDataSource: TmpTimeDataSource
) : TmpTimeRepository {


    /*
        DoingReceiver
    */


    override suspend fun addTmpTimeModel(
        currentTime: String,
        tmpTimeModel: TmpTimeModel,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        tmpTimeDataSource.addTmpTimeEntity(
            currentTime,
            TmpTimeMapper.mapperToTmpTimeEntity(tmpTimeModel),
        ) { result ->
            onResult(result)
        }
    }

    /*
        TmpTimeActivity
     */

    override suspend fun deleteTmpTimeModel(
        startTime: String,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        tmpTimeDataSource.deleteTmpTimeEntity(
            startTime
        ) { result ->
            onResult(result)
        }
    }


    override suspend fun getTmpTimeModelList(
        onResult: (DatabaseResult<List<TmpTimeModel>>) -> Unit
    ) {
        tmpTimeDataSource.getTmpTimeEntityList { result ->
            when(result) {
                is DatabaseResult.Success -> {
                    onResult(
                        DatabaseResult.Success(
                            result.data.map { entity ->
                                TmpTimeMapper.mapperToTmpTimeModel(entity)
                            }
                        )
                    )
                }
                is DatabaseResult.Failure -> {
                    onResult(
                        DatabaseResult.Failure(
                            result.message
                        )
                    )
                }
            }
        }

    }

    override suspend fun updateTmpTimeDayModel(
        wakeUpTime1: String,
        wakeupTime2: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        tmpTimeDataSource.updateTmpTimeDayEntity(
            wakeUpTime1,
            wakeupTime2,
            count
        ) { result ->
            onResult(result)
        }
    }

    override suspend fun updateTmpTimeMonthModel(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        tmpTimeDataSource.updateTmpTimeMonthEntity(
            wakeUpTime1,
            wakeUpTime2,
            count
        ) { result ->
            onResult(result)
        }
    }

    override suspend fun updateTmpTimeYearModel(
        wakeUpTime: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        tmpTimeDataSource.updateTmpTimeYearEntity(
            wakeUpTime,
            count
        ) { result ->
            onResult(result)
        }
    }
}