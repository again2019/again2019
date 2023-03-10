package com.example.data.repositoryImpl

import com.example.data.dataSource.savedTimeDataSource.SavedTimeDataSource
import com.example.data.mapper.SavedTimeMapper
import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel
import com.example.domain.repository.SavedTimeRepository
import javax.inject.Inject

class SavedTimeRepositoryImpl @Inject constructor(
    private val savedTimeDataSource: SavedTimeDataSource
): SavedTimeRepository {

    // savedTimeAboutRank

    override suspend fun getSavedTimeAboutMonthRankModel(): ArrayList<SavedTimeAboutRankModel> {
        return savedTimeDataSource.getSavedTimeAboutMonthRankEntity().map {
            SavedTimeMapper.mapperToSavedTimeAboutRankModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getSavedTimeAboutYearRankModel(): ArrayList<SavedTimeAboutRankModel> {
        return savedTimeDataSource.getSavedTimeAboutYearRankEntity().map {
            SavedTimeMapper.mapperToSavedTimeAboutRankModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun updateSavedTimeAboutMonthRankModel(yyyyMM: String, count: Double) {
        savedTimeDataSource.getSavedTimeAboutMonthRankEntity()
    }

    override suspend fun updateSavedTimeAboutYearRankModel(yyyy: String, count: Double) {
        savedTimeDataSource.getSavedTimeAboutYearRankEntity()
    }

    // savedTime

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