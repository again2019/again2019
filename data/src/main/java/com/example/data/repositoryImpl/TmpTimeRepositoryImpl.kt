package com.example.data.repositoryImpl

import com.example.data.dataSource.tmpTimeDataSource.TmpTimeDataSource
import com.example.data.mapper.TmpTimeMapper
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.TmpTimeRepository
import javax.inject.Inject


class TmpTimeRepositoryImpl @Inject constructor (
    private val tmpTimeDataSource: TmpTimeDataSource
) : TmpTimeRepository {


    /*
    TmpTimeActivity
     */

    // 임시 저장된 정보를 가져오는 코드
    override suspend fun getTmpTimeModel(): ArrayList<TmpTimeModel> {
        return tmpTimeDataSource.getTmpTimeEntity().map {
            TmpTimeMapper.mapperToTmpTimeModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun deleteTmpTimeModel(startTime: String) {
        tmpTimeDataSource.deleteTmpTimeEntity(startTime)
    }

    override suspend fun updateTmpTimeDayModel(
        wakeUpTime1: String,
        wakeupTime2: String,
        count: Double
    ) {
        tmpTimeDataSource.updateTmpTimeDayEntity(wakeUpTime1, wakeupTime2, count)
    }

    override suspend fun updateTmpTimeMonthModel(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double
    ) {
        tmpTimeDataSource.updateTmpTimeMonthEntity(wakeUpTime1, wakeUpTime2, count)
    }

    override suspend fun updateTmpTimeYearModel(wakeUpTime: String, count: Double) {
        tmpTimeDataSource.updateTmpTimeYearEntity(wakeUpTime, count)
    }

}