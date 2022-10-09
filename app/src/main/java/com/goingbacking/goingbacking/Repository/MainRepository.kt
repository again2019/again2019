package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore

class MainRepository (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ): MainRepositoryIF {
    override fun getFifthUserInfo(): UiState<UserInfoDTO> {


        val data = UserInfoDTO()

        if (data.uid == null) {
            return UiState.Failure("UserInfo is Empty")
        } else {
            return UiState.Success(data)

        }
    }
}