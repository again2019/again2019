package com.example.data.mapper

import com.example.data.entity.SavedTimeDayEntity
import com.example.data.entity.SavedTimeMonthEntity
import com.example.data.entity.SavedTimeYearEntity
import com.example.data.entity.UserInfoEntity
import com.example.domain.model.SavedTimeDayModel
import com.example.domain.model.SavedTimeMonthModel
import com.example.domain.model.SavedTimeYearModel
import com.example.domain.model.UserInfoModel

object SavedTimeMapper {

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