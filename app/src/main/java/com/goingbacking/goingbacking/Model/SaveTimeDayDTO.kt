package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveTimeDayDTO (
    var count: Int? = null,
    var day: Int? = null,
    var month: Int? = null,
    var year: Int? = null
        ): Parcelable
