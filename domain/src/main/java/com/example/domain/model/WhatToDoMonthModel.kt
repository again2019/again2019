package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WhatToDoMonthModel(
    var count: Int? = null,
    var month: Int? = null,
    var whatToDo :String? = null
): Parcelable