package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    var dest: String,
    var date: String,
    var start: Int,
    var start_t: Int,
    var end: Int,
    var end_t: Int,
): Parcelable
