package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WhatToDoYearModel (
    var count :Int? = null,
    var year :Int? = null,
    var whatToDo :String? = null
     ): Parcelable
