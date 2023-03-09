package com.example.data.mapper

import com.example.data.entity.TmpTimeEntity
import com.example.domain.model.TmpTimeModel

object TmpTimeMapper {

    fun mapperToTmpTimeDTO(tmpTime: TmpTimeEntity) : TmpTimeModel {
        return TmpTimeModel(
            nowSeconds = tmpTime.nowSeconds,
            wakeUpTime = tmpTime.wakeUpTime,
            startTime = tmpTime.startTime
        )
    }

    fun mapperToTmpTimeEntity(tmpTime: TmpTimeModel) : TmpTimeEntity {
        return TmpTimeEntity(
            nowSeconds = tmpTime.nowSeconds,
            wakeUpTime = tmpTime.wakeUpTime,
            startTime = tmpTime.startTime
        )
    }
}