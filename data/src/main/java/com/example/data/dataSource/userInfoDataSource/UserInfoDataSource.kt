package com.example.data.dataSource.userInfoDataSource

import com.example.data.entity.UserInfoEntity

interface UserInfoDataSource {

    // input

    suspend fun addUserInfoEntity(userNickName: String)

    suspend fun updateUserType(userType: String)

    suspend fun updateUserSelectedList(selected: List<String>)


    suspend fun getMyUserInfoEntity() : UserInfoEntity

    suspend fun getOtherUserInfoEntity(destinationUid: String) : UserInfoEntity

    suspend fun updateUserInfoEntity(nickname :String, type :String, selected : List<String>)

    suspend fun updateCheerList(destinationUid: String, cheer: String) : List<String>

    suspend fun deleteCheer(destinationUid: String, cheer: String)

    suspend fun updateLikeButton(destinationUid :String, state :String) : String
}