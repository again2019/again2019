package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedTimeYearEntity (
    var count :Int = 0,
    var year : Int? = 0,
): Parcelable