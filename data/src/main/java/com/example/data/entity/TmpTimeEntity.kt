package com.example.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TmpTimeEntity (
    var nowSeconds: Long? = null,
    var startTime : Long? = null,
    var wakeUpTime : Long? = null
) : Parcelable