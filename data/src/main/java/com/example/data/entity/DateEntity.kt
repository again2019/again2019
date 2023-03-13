package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateDTO (
    var dateList : List<String> = listOf()
) : Parcelable