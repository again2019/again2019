package com.goingbacking.goingbacking.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoDTO (
    var userNickName : String? = null,
    var userType : String? = null,
    var whatToDoList : List<String> = listOf(),
    var uid : String? = null,
    var token : String? = null,
    var likes : List<String> = listOf(),
    var cheers : List<String> = listOf()
) : Parcelable
