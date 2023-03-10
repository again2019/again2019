package com.example.domain.repository

import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel

interface WhatToDoRepository {

    // WhatToDoMonth

    suspend fun addWhatToDoMonthModel(whatToDoMonthModel: WhatToDoMonthModel)


    // WhatToDoYear

    suspend fun addWhatToDoYearModel(whatToDoYearModel: WhatToDoYearModel)


}