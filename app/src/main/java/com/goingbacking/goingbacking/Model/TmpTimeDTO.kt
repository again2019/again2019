package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TmpTimeDTO (
    var nowSeconds: Long? = null,
    var startTime : Long? = null,
    var wakeUpTime : Long? = null
        ): Parcelable