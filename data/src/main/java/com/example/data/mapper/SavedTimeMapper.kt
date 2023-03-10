package com.example.data.mapper

import com.example.data.entity.*
import com.example.domain.model.*

object SavedTimeMapper {

    // SavedTimeAboutRank

    fun mapperToSavedTimeAboutRankModel(savedTimeAboutRank: SavedTimeAboutRankEntity) : SavedTimeAboutRankModel {
        return SavedTimeAboutRankModel(
            uid = savedTimeAboutRank.uid,
            count = savedTimeAboutRank.count,
            nickname =  savedTimeAboutRank.nickname
        )
    }

    fun mapperToSavedTimeAboutRankEntity(savedTimeAboutRank: SavedTimeAboutRankModel) : SavedTimeAboutRankEntity {
        return SavedTimeAboutRankEntity(
            uid = savedTimeAboutRank.uid,
            count = savedTimeAboutRank.count,
            nickname =  savedTimeAboutRank.nickname
        )
    }

    // SavedTimeDay

    fun mapperToSavedTimeDayModel(savedTimeDay: SavedTimeDayEntity) : SavedTimeDayModel {
        return SavedTimeDayModel(
            count = savedTimeDay.count,
            day = savedTimeDay.day,
            month = savedTimeDay.month,
            year = savedTimeDay.year,
        )
    }

    fun mapperToSavedTimeDayEntity(savedTimeDay: SavedTimeDayModel) : SavedTimeDayEntity {
        return SavedTimeDayEntity(
            count = savedTimeDay.count,
            day = savedTimeDay.day,
            month = savedTimeDay.month,
            year = savedTimeDay.year,
        )
    }

    // SavedTimeMonth

    fun mapperToSavedTimeMonthModel(savedTimeMonth: SavedTimeMonthEntity) : SavedTimeMonthModel {
        return SavedTimeMonthModel(
            count = savedTimeMonth.count,
            month = savedTimeMonth.month,
            year = savedTimeMonth.year,
        )
    }

    fun mapperToSavedTimeDayEntity(savedTimeMonth: SavedTimeMonthModel) : SavedTimeMonthEntity {
        return SavedTimeMonthEntity(
            count = savedTimeMonth.count,
            month = savedTimeMonth.month,
            year = savedTimeMonth.year,
        )
    }

    // SavedTimeYear

    fun mapperToSavedTimeYearModel(savedTimeYear: SavedTimeYearEntity) : SavedTimeYearModel {
        return SavedTimeYearModel(
            count = savedTimeYear.count,
            year = savedTimeYear.year,
        )
    }

    fun mapperToSavedTimeYearEntity(savedTimeYear: SavedTimeYearModel) : SavedTimeYearEntity {
        return SavedTimeYearEntity(
            count = savedTimeYear.count,
            year = savedTimeYear.year,
        )
    }


}