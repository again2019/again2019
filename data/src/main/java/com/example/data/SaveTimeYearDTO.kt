package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveTimeYearDTO (
    var count :Int,
    var year : Int?,
): Parcelable