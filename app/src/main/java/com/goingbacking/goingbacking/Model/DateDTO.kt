package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateDTO (
    var dateList : List<String> = listOf()
) : Parcelable