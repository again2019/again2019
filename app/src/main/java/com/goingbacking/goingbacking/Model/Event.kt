package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    var dest: String? = null,
    var date: String? = null,
    var start: Int? = null,
    var start_t: Int? = null,
    var end: Int? = null,
    var end_t: Int? = null,
): Parcelable
