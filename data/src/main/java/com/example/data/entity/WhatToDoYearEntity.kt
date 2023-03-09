package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WhatToDoYearEntity (
    var count :Int? = null,
    var year :Int? = null,
    var whatToDo :String? = null
     ): Parcelable
