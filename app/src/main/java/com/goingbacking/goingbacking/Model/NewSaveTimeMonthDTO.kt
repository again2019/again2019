package com.goingbacking.goingbacking.Model

data class NewSaveTimeMonthDTO (
    val uid :String? = null,
    val count : Int? = null,
    val type: String? = null,
    val whattodo :String? =null,
    val nickname :String? = null,
    val likes: List<String> = listOf<String>()
        )