package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedTimeAboutRankModel (
    var uid :String = "",
    var count : Int = 0,
    var nickname :String = "",
): Parcelable