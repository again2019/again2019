package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedTimeMonthModel (
    var month :Int = 0,
    var year : Int = 0,
    var count : Int = 0,
) : Parcelable