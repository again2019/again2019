package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateEntity (
    var dateList : List<String> = listOf()
) : Parcelable