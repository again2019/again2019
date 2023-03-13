package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DateModel (
    var dateList : List<String> = listOf()
) : Parcelable