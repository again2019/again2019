package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleEntity(
    var dest: String = "",
    var date: String = "",
    var start: Int = 0,
    var start_t: Int = 0,
    var end: Int = 0,
    var end_t: Int = 0,
): Parcelable
