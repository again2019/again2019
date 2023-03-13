package com.example.data.dataSource.scheduleDataSource

import com.example.data.entity.SavedTimeDayEntity
import com.example.data.entity.ScheduleEntity
import java.time.LocalDate

interface ScheduleDataSource {

    suspend fun addScheduleEntity(path1: String, path2: String, schedule: ScheduleEntity)

    suspend fun deleteScheduleEntites(scheduleDate: String, timeStamp: String) : MutableMap<LocalDate, List<ScheduleEntity>>

    suspend fun getAllScheduleEntities() : MutableMap<LocalDate, List<ScheduleEntity>>

}