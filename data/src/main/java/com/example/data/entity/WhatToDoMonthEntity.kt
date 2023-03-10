package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WhatToDoMonthEntity (
    var count: Int? = null,
    var month: Int? = null,
    var whatToDo :String? = null
): Parcelable