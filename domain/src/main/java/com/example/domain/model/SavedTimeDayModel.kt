package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SavedTimeDayModel (
    var count: Int = 0,
    var day: Int = 0,
    var month: Int = 0,
    var year: Int = 0,
): Parcelable
