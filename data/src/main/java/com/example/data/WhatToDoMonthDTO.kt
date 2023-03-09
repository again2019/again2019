package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WhatToDoMonthDTO(
    var count: Int? = null,
    var month: Int? = null,
    var whatToDo :String? = null
): Parcelable