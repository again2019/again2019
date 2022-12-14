package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewSaveTimeYearDTO (
    val uid :String? = null,
    val count : Int? = null,
    val nickname :String? =null,
) : Parcelable