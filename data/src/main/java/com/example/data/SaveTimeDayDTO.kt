package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SaveTimeDayDTO (
    var count: Int,
    var day: Int,
    var month: Int,
    var year: Int,
): Parcelable
