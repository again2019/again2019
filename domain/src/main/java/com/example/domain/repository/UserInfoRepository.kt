package com.example.domain.repository

import com.example.domain.model.UserInfoModel

interface UserInfoRepository {

    // input

    suspend fun addUserInfoModel(userNickName: String)

    suspend fun updateUserType(userType: String)

    suspend fun updateUserSelectedList(selected: List<String>)

    suspend fun getMyUserInfoModel(): UserInfoModel

    suspend fun getOtherUserInfoModel(destinationUid: String): UserInfoModel

    suspend fun updateUserInfoModel(nickname: String, type: String, selected: List<String>)

    suspend fun updateCheerList(destinationUid: String, cheer: String): List<String>

    suspend fun deleteCheer(destinationUid: String, cheer: String)

    suspend fun updateLikeButton(destinationUid: String, state: String): String

}
