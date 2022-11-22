package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveTimeYearDTO (
    var count :Int? = 0,
    var year : Int? = 0
        ): Parcelable