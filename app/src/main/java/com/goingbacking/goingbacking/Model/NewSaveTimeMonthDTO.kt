package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewSaveTimeMonthDTO (
    val uid :String? = null,
    var count : Int? = null,
    val nickname :String? = null,
): Parcelable