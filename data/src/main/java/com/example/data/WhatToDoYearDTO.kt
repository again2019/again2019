package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WhatToDoYearDTO (
    var count :Int? = null,
    var year :Int? = null,
    var whatToDo :String? = null
     ): Parcelable
