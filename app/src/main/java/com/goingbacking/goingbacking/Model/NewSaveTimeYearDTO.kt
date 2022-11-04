package com.goingbacking.goingbacking.Model

data class NewSaveTimeYearDTO (
    val uid :String? = null,
    val count : Int? = null,
    val nickname :String? =null,
    val type: String? = null,
    val whattodo :List<String> = listOf(),
    val likes: List<String> = listOf(),
    val cheers: List<String> = listOf()

)