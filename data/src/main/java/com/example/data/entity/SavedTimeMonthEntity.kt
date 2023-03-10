package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedTimeMonthEntity (
    var month :Int = 0,
    var year : Int = 0,
    var count : Int = 0,
) : Parcelable