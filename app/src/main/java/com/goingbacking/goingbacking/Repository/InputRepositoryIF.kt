package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState

interface InputRepositoryIF {
    fun addFirstInput(userInfoDTO : UserInfoDTO, result: (UiState<String>) -> Unit)
    fun updateSecondInput(userType: String, result: (UiState<String>) -> Unit)
}