package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedTimeYearModel (
    var count :Int = 0,
    var year : Int? = 0,
): Parcelable