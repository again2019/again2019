package com.example.data.repositoryImpl

import com.example.data.dataSource.scheduleAndDateDataSource.ScheduleAndDateDataSource
import com.example.data.mapper.ScheduleAndDateMapper
import com.example.domain.model.DateModel
import com.example.domain.model.ScheduleModel
import com.example.domain.repository.ScheduleAndDateRepository
import java.time.LocalDate
import javax.inject.Inject

class SchedueAndDateRepositoryImpl @Inject constructor(
    private val scheduleAndDateDataSource: ScheduleAndDateDataSource
) : ScheduleAndDateRepository {
    override suspend fun addDateModel(yearMonth: String, date: DateModel) {
        scheduleAndDateDataSource.addDateEntity(yearMonth, ScheduleAndDateMapper.mapperToDateEntity(date))
    }

    override suspend fun addScheduleModel(path1: String, path2: String, schedule: ScheduleModel) {
        scheduleAndDateDataSource.addScheduleEntity(path1, path2, ScheduleAndDateMapper.mapperToScheduleEntity(schedule))
    }

    override suspend fun getDateModel(yearMonth: String): DateModel? {
        return ScheduleAndDateMapper.mapperToDateModel(scheduleAndDateDataSource.getDateEntity(yearMonth))
    }

    override suspend fun getDateModelList(yearMonth: String): List<String> {
        return scheduleAndDateDataSource.getDateEntityList(yearMonth)
    }


    override suspend fun deleteScheduleModels(
        scheduleDate: String,
        timeStamp: String
    ): MutableMap<LocalDate, List<ScheduleModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSelectedScheduleModels(
        yearMonth: String,
        date: String
    ): List<ScheduleModel> {
        return scheduleAndDateDataSource.getSelectedScheduleEntities(yearMonth, date).map {
            ScheduleAndDateMapper.mapperToScheduleModel(it)
        }
    }

    override suspend fun getAllScheduleModels(): MutableMap<LocalDate, List<ScheduleModel>> {
        TODO("Not yet implemented")
    }



}