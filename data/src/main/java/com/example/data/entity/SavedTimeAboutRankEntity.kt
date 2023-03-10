package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedTimeAboutRankEntity (
    var uid :String = "",
    var count : Int = 0,
    var nickname :String = "",
): Parcelable