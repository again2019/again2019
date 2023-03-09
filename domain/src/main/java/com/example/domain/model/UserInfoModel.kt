package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfoModel (
    var userNickName : String? = null,
    var userType : String? = null,
    var whatToDoList : List<String> = listOf(),
    var uid : String? = null,
    var token : String? = null,
    var likes : List<String> = listOf(),
    var cheers : List<String> = listOf()
) : Parcelable