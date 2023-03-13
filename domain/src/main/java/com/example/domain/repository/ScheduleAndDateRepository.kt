package com.example.domain.repository

import com.example.domain.model.DateModel
import com.example.domain.model.ScheduleModel
import java.time.LocalDate

interface ScheduleAndDateRepository {
    suspend fun addDateModel(yearMonth: String, date: DateModel)

    suspend fun addScheduleModel(path1: String, path2: String, schedule: ScheduleModel)

    suspend fun getDateModel(yearMonth: String) : DateModel

    suspend fun getDateModelList(yearMonth: String) : List<String>

    suspend fun deleteScheduleModels(scheduleDate: String, timeStamp: String) : MutableMap<LocalDate, List<ScheduleModel>>

    suspend fun getSelectedScheduleModels(yearMonth: String, date: String) : List<ScheduleModel>

    suspend fun getAllScheduleModels() : MutableMap<LocalDate, List<ScheduleModel>>

}