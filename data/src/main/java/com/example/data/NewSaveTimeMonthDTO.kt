package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewSaveTimeMonthDTO (
    val uid :String,
    var count : Int,
    val nickname :String,
): Parcelable