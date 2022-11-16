package com.goingbacking.goingbacking.Model

data class UserInfoDTO (
    var userNickName : String? = null,
    var userType : String? = null,
    var whatToDoList : List<String> = listOf(),
    var uid : String? = null,
    var token : String? = null,
    var likes : List<String> = listOf(),
    var cheers : List<String> = listOf()
)
