package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SaveTimeMonthDTO (
    var month :Int? = null,
    var year : Int? = null,
    var count : Int? = null
        ) : Parcelable