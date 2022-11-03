package com.goingbacking.goingbacking.Model

data class UserInfoDTO (
    var userNickName : String? = null,
    var userType : String? = null,
    var whatToDo : String? = null,
    var whatToDoList : List<String> = listOf(),
    var uid : String? = null,
)
