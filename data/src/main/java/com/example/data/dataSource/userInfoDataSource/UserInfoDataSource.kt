package com.example.data.dataSource.userInfoDataSource

import com.example.data.entity.UserInfoEntity

interface UserInfoDataSource {

    suspend fun getUserInfoEntity() : UserInfoEntity

    suspend fun updateUserInfoEntity(nickname :String, type :String, selected : List<String>)

}