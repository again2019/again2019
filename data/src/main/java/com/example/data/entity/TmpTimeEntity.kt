package com.example.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TmpTimeEntity (
    var nowSeconds: Long = 0L,
    var startTime : Long = 0L,
    var wakeUpTime : Long = 0L
) : Parcelable