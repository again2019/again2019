package com.example.domain.repository

import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel

interface WhatToDoRepository {

    // WhatToDoMonth

    suspend fun addWhatToDoMonthModel(whatToDoMonthModel: WhatToDoMonthModel)

    suspend fun updateWhatToDoMonthModel(yyyyMM :String, whatToDo: String, count: Double)

    suspend fun getMyWhatToDoMonthEntity() : ArrayList<WhatToDoMonthModel>

    suspend fun getOtherWhatToDoMonthEntity(destinationUid: String) : ArrayList<WhatToDoMonthModel>

    // WhatToDoYear

    suspend fun addWhatToDoYearModel(whatToDoYearModel: WhatToDoYearModel)

    suspend fun updateWhatToDoYearModel(yyyy :String, whatToDo: String, count: Double)

    suspend fun getMyWhatToDoYearEntity() : ArrayList<WhatToDoYearModel>

    suspend fun getOtherWhatToDoYearEntity(destinationUid: String) : ArrayList<WhatToDoYearModel>

}