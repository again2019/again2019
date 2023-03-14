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

    // 가장 처음에 추가되는 Entity
    override suspend fun addMyInitSavedTimeMonthModel(savedTimeMonthModel: SavedTimeMonthModel) {
        return savedTimeDataSource.addMySavedTimeMonthEntity(
            SavedTimeMapper.mapperToSavedTimeMonthEntity(savedTimeMonthModel)
        )
    }

    override suspend fun addMyInitSavedTimeYearModel(savedTimeYearModel: SavedTimeYearModel) {
        return savedTimeDataSource.addMySavedTimeYearEntity(
            SavedTimeMapper.mapperToSavedTimeYearEntity(savedTimeYearModel)
        )
    }

    override suspend fun addMySavedTimeAboutMonthRankModel(savedTimeAboutMonthRankModel: SavedTimeAboutRankModel) {
        savedTimeDataSource.addMySavedTimeAboutMonthRankEntity(
            SavedTimeMapper.mapperToSavedTimeAboutRankEntity(savedTimeAboutMonthRankModel)
        )
    }

    override suspend fun addMySavedTimeAboutYearRankModel(savedTimeAboutYearRankModel: SavedTimeAboutRankModel) {
        savedTimeDataSource.addMySavedTimeAboutYearRankEntity(
            SavedTimeMapper.mapperToSavedTimeAboutRankEntity(savedTimeAboutYearRankModel)
        )
    }

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

    // mySavedTime

    override suspend fun getMySavedTimeDayModel(): ArrayList<SavedTimeDayModel> {
        return savedTimeDataSource.getMySavedTimeDayEntity().map {
            SavedTimeMapper.mapperToSavedTimeDayModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getMySavedTimeMonthModel(): ArrayList<SavedTimeMonthModel> {
        return savedTimeDataSource.getMySavedTimeMonthEntity().map {
            SavedTimeMapper.mapperToSavedTimeMonthModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getMySavedTimeYearModel(): ArrayList<SavedTimeYearModel> {
        return savedTimeDataSource.getMySavedTimeYearEntity().map {
            SavedTimeMapper.mapperToSavedTimeYearModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun addMySavedTimeDayModel(savedTimeDayModel: SavedTimeDayModel) {
        return savedTimeDataSource.addMySavedTimeDayEntity(
            SavedTimeMapper.mapperToSavedTimeDayEntity(savedTimeDayModel)
        )
    }

    override suspend fun addMySavedTimeMonthModel(savedTimeMonthModel: SavedTimeMonthModel) {
        return savedTimeDataSource.addMySavedTimeMonthEntity(
            SavedTimeMapper.mapperToSavedTimeMonthEntity(savedTimeMonthModel)
        )
    }

    override suspend fun addMySavedTimeYearModel(savedTimeYearModel: SavedTimeYearModel) {
        return savedTimeDataSource.addMySavedTimeYearEntity(
            SavedTimeMapper.mapperToSavedTimeYearEntity(savedTimeYearModel)
        )
    }

    // otherSavedTime

    override suspend fun getOtherSavedTimeDayModel(destinationUid: String): ArrayList<SavedTimeDayModel> {
        return savedTimeDataSource.getOtherSavedTimeDayEntity(destinationUid).map {
            SavedTimeMapper.mapperToSavedTimeDayModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getOtherSavedTimeMonthModel(destinationUid: String): ArrayList<SavedTimeMonthModel> {
        return savedTimeDataSource.getOtherSavedTimeMonthEntity(destinationUid).map {
            SavedTimeMapper.mapperToSavedTimeMonthModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getOtherSavedTimeYearModel(destinationUid: String): ArrayList<SavedTimeYearModel> {
        return savedTimeDataSource.getOtherSavedTimeYearEntity(destinationUid).map {
            SavedTimeMapper.mapperToSavedTimeYearModel(it)
        }.toCollection(ArrayList())
    }



}