package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveTimeMonthDTO (
    var month :Int,
    var year : Int,
    var count : Int,
) : Parcelable