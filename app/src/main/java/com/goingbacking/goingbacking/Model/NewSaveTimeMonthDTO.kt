package com.goingbacking.goingbacking.Model

data class NewSaveTimeMonthDTO (
    val uid :String? = null,
    var count : Int? = null,
    val type: String? = null,
    val token: String? = null,
    val whattodo :List<String> = listOf(),
    val nickname :String? = null,
    val likes: List<String> = listOf(),
    val cheers: List<String> = listOf()
)