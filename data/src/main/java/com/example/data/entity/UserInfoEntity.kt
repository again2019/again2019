package com.example.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoEntity (
    var userNickName : String? = null,
    var userType : String? = null,
    var whatToDoList : List<String> = listOf(),
    var uid : String? = null,
    var token : String? = null,
    var likes : List<String> = listOf(),
    var cheers : List<String> = listOf()
) : Parcelable