package com.example.data.mapper

import com.example.data.entity.DateEntity
import com.example.data.entity.SavedTimeAboutRankEntity
import com.example.data.entity.ScheduleEntity
import com.example.domain.model.DateModel
import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.model.ScheduleModel

object ScheduleAndDateMapper {

    // ScheduleEntity

    fun mapperToScheduleModel(schedule: ScheduleEntity) : ScheduleModel {
        return ScheduleModel(
            date = schedule.date,
            dest =  schedule.dest,
            start = schedule.start,
            start_t = schedule.start_t,
            end = schedule.end,
            end_t = schedule.end_t,
        )
    }

    fun mapperToScheduleEntity(schedule: ScheduleModel) : ScheduleEntity {
        return ScheduleEntity(
            date = schedule.date,
            dest =  schedule.dest,
            start = schedule.start,
            start_t = schedule.start_t,
            end = schedule.end,
            end_t = schedule.end_t,
        )
    }

    fun mapperToDateEntity(date: DateModel) : DateEntity {
        return DateEntity(
            dateList = date.dateList
        )
    }

    fun mapperToDateModel(date: DateEntity) : DateModel {
        return DateModel(
            dateList = date.dateList
        )
    }


}