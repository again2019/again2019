package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState

interface MainRepositoryIF {

    fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit)

}