package com.example.data.repositoryImpl.whatToDoRepository

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

    // WhatToDoYear

    override suspend fun addWhatToDoYearModel(
        whatToDoYearModel: WhatToDoYearModel
    ) {
        whatToDoDataSource.addWhatToDoYearEntity(
            WhatToDoMapper.mapperToWhatToDoYearEntity(whatToDoYearModel)
        )
    }
}