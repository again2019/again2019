package com.example.domain.repository

import com.example.domain.model.UserInfoModel

interface UserInfoRepository {

    suspend fun getUserInfoModel() : UserInfoModel

    suspend fun updateUserInfoModel(nickname :String, type :String, selected : List<String>)


}