package com.example.data.datasource.whatToDoDataSource

import com.example.data.entity.WhatToDoMonthEntity
import com.example.data.entity.WhatToDoYearEntity

interface WhatToDoDataSource {

    // WhatToDoMonth

    suspend fun addWhatToDoMonthEntity(whatToDoMonthEntity: WhatToDoMonthEntity)

    suspend fun updateWhatToDoMonthEntity(yyyyMM :String, whatToDo: String, count: Double)

    suspend fun getMyWhatToDoMonthEntity() : ArrayList<WhatToDoMonthEntity>

    suspend fun getOtherWhatToDoMonthEntity(destinationUid: String) : ArrayList<WhatToDoMonthEntity>

    // WhatToDoYear

    suspend fun addWhatToDoYearEntity(whatToDoYearEntity: WhatToDoYearEntity)

    suspend fun updateWhatToDoYearEntity(yyyy :String, whatToDo: String, count: Double)

    suspend fun getMyWhatToDoYearEntity() : ArrayList<WhatToDoYearEntity>

    suspend fun getOtherWhatToDoYearEntity(destinationUid: String) : ArrayList<WhatToDoYearEntity>
}