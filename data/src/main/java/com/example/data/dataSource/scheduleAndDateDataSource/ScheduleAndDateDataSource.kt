package com.example.data.dataSource.scheduleAndDateDataSource

import com.example.data.entity.DateEntity
import com.example.data.entity.ScheduleEntity
import java.time.LocalDate

interface ScheduleAndDateDataSource {



    suspend fun addDateEntity(yearMonth: String, date: DateEntity)

    suspend fun addScheduleEntity(path1: String, path2: String, schedule: ScheduleEntity)

    suspend fun getDateEntity(yearMonth: String) : DateEntity

    suspend fun getDateEntityList(yearMonth: String) : List<String>

    suspend fun deleteScheduleEntites(scheduleDate: String, timeStamp: String) : MutableMap<LocalDate, List<ScheduleEntity>>

    suspend fun getSelectedScheduleEntities(yearMonth: String, date: String) : List<ScheduleEntity>

    suspend fun getAllScheduleEntities() : MutableMap<LocalDate, List<ScheduleEntity>>




}