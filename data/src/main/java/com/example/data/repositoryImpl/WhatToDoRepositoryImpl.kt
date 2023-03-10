package com.example.data.repositoryImpl

import com.example.data.dataSource.whatToDoDataSource.WhatToDoDataSource
import com.example.data.mapper.WhatToDoMapper
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel
import com.example.domain.repository.WhatToDoRepository
import javax.inject.Inject

class WhatToDoRepositoryImpl @Inject constructor (
    private val whatToDoDataSource: WhatToDoDataSource
) : WhatToDoRepository {

    // WhatToDoMonth

    override suspend fun addWhatToDoMonthModel(
        whatToDoMonthModel: WhatToDoMonthModel
    ) {
        whatToDoDataSource.addWhatToDoMonthEntity(
            WhatToDoMapper.mapperToWhatToDoMonthEntity(whatToDoMonthModel)
        )
    }

    override suspend fun updateWhatToDoMonthModel(yyyyMM: String, whatToDo: String, count: Double) {
        whatToDoDataSource.updateWhatToDoMonthEntity(yyyyMM, whatToDo, count)
    }

    override suspend fun getMyWhatToDoMonthEntity(): ArrayList<WhatToDoMonthModel> {
         return whatToDoDataSource.getMyWhatToDoMonthEntity().map {
             WhatToDoMapper.mapperToWhatToDoMonthModel(it)
         }.toCollection(ArrayList())
    }

    override suspend fun getOtherWhatToDoMonthEntity(destinationUid: String): ArrayList<WhatToDoMonthModel> {
        return whatToDoDataSource.getOtherWhatToDoMonthEntity(destinationUid).map {
            WhatToDoMapper.mapperToWhatToDoMonthModel(it)
        }.toCollection(ArrayList())
    }

    // WhatToDoYear

    override suspend fun addWhatToDoYearModel(
        whatToDoYearModel: WhatToDoYearModel
    ) {
        whatToDoDataSource.addWhatToDoYearEntity(
            WhatToDoMapper.mapperToWhatToDoYearEntity(whatToDoYearModel)
        )
    }

    override suspend fun updateWhatToDoYearModel(yyyy: String, whatToDo: String, count: Double) {
        whatToDoDataSource.updateWhatToDoYearEntity(yyyy, whatToDo, count)
    }

    override suspend fun getMyWhatToDoYearEntity(): ArrayList<WhatToDoYearModel> {
        return whatToDoDataSource.getMyWhatToDoYearEntity().map {
            WhatToDoMapper.mapperToWhatToDoYearModel(it)
        }.toCollection(ArrayList())
    }

    override suspend fun getOtherWhatToDoYearEntity(destinationUid: String): ArrayList<WhatToDoYearModel> {
        return whatToDoDataSource.getOtherWhatToDoYearEntity(destinationUid).map {
            WhatToDoMapper.mapperToWhatToDoYearModel(it)
        }.toCollection(ArrayList())
    }
}