package com.goingbacking.goingbacking.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfoDTO (
    var userNickName : String? = null,
    var userType : String? = null,
    var whatToDo : String? = null,
    var uid : String? = null,
) : Parcelable
