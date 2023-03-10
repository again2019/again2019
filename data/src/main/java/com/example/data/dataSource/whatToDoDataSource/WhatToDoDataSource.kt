package com.example.data.dataSource.whatToDoDataSource

import com.example.data.entity.WhatToDoMonthEntity
import com.example.data.entity.WhatToDoYearEntity

interface WhatToDoDataSource {

    // WhatToDoMonth

    suspend fun addWhatToDoMonthEntity(whatToDoMonthEntity: WhatToDoMonthEntity)


    // WhatToDoYear

    suspend fun addWhatToDoYearEntity(whatToDoYearEntity: WhatToDoYearEntity)


}