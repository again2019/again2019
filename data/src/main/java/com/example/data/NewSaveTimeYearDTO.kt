package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewSaveTimeYearDTO (
    val uid :String,
    val count : Int,
    val nickname :String,
) : Parcelable