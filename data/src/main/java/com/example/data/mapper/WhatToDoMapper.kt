package com.example.data.mapper

import com.example.data.entity.UserInfoEntity
import com.example.data.entity.WhatToDoMonthEntity
import com.example.data.entity.WhatToDoYearEntity
import com.example.domain.model.UserInfoModel
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel

object WhatToDoMapper {

    // WhatToDoMonth

    fun mapperToWhatToDoMonthModel(whatToDoMonth: WhatToDoMonthEntity) : WhatToDoMonthModel {
        return WhatToDoMonthModel(
            count = whatToDoMonth.count,
            month = whatToDoMonth.month,
            whatToDo = whatToDoMonth.whatToDo,
        )
    }

    fun mapperToWhatToDoMonthEntity(whatToDoMonth: WhatToDoMonthModel) : WhatToDoMonthEntity {
        return WhatToDoMonthEntity(
            count = whatToDoMonth.count,
            month = whatToDoMonth.month,
            whatToDo = whatToDoMonth.whatToDo,
        )
    }

    // WhatToDoYear

    fun mapperToWhatToDoYearModel(whatToDoYear: WhatToDoYearEntity) : WhatToDoYearModel {
        return WhatToDoYearModel(
            count = whatToDoYear.count,
            year = whatToDoYear.year,
            whatToDo = whatToDoYear.whatToDo,
        )
    }

    fun mapperToWhatToDoYearEntity(whatToDoYear: WhatToDoYearModel) : WhatToDoYearEntity {
        return WhatToDoYearEntity(
            count = whatToDoYear.count,
            year = whatToDoYear.year,
            whatToDo = whatToDoYear.whatToDo,
        )
    }


}