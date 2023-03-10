package com.example.data.repositoryImpl

import com.example.data.dataSource.savedTimeDataSource.SavedTimeDataSource
import com.example.data.mapper.SavedTimeMapper
import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel
import com.example.domain.repository.SavedTimeRepository
import javax.inject.Inject

class SavedTimeRepositoryImpl @Inject constructor(
    private val savedTimeDataSource: SavedTimeDataSource
): SavedTimeRepository {
    override suspend fun getSavedTimeDayModel(): ArrayList<SavedTimeDayModel> {
        return savedTimeDataSource.getSavedTimeDayEntity().map {
            SavedTimeMapper.mapperToSavedTimeDayModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getSavedTimeMonthModel(): ArrayList<SavedTimeMonthModel> {
        return savedTimeDataSource.getSavedTimeMonthEntity().map {
            SavedTimeMapper.mapperToSavedTimeMonthModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getSavedTimeYearModel(): ArrayList<SavedTimeYearModel> {
        return savedTimeDataSource.getSavedTimeYearEntity().map {
            SavedTimeMapper.mapperToSavedTimeYearModel(it)
        }.toCollection(ArrayList())
    }
}