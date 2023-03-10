package com.goingbacking.goingbacking.repository.first

import com.example.data.dataSource.tmpTimeDataSource.TmpTimeDataSource
import com.example.data.mapper.TmpTimeMapper
import com.example.domain.model.TmpTimeModel
import com.example.domain.repository.FirstRepository
import javax.inject.Inject


class FirstRepositoryImpl @Inject constructor (
    private val tmpTimeDataSource: TmpTimeDataSource
) : FirstRepository {


    /*
    TmpTimeActivity
     */

    // 임시 저장된 정보를 가져오는 코드


    override suspend fun getTmpTime(): ArrayList<TmpTimeModel> {
        return tmpTimeDataSource.getTmpTimDTO().map {
            TmpTimeMapper.mapperToTmpTimeDTO(it)
        }.toCollection(ArrayList())
    }


}