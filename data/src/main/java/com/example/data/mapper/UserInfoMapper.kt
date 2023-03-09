package com.example.data.mapper

import com.example.data.entity.UserInfoEntity
import com.example.domain.model.UserInfoModel

object UserInfoMapper {
    fun mapperToUserInfoModel(userInfo: UserInfoEntity) : UserInfoModel {
        return UserInfoModel(
            userNickName = userInfo.userNickName,
            userType = userInfo.userType,
            whatToDoList = userInfo.whatToDoList,
            uid = userInfo.uid,
            token = userInfo.token,
            likes = userInfo.likes,
            cheers = userInfo.cheers,
        )
    }

    fun mapperToUserInfoEntity(userInfo: UserInfoModel) : UserInfoEntity {
        return UserInfoEntity(
            userNickName = userInfo.userNickName,
            userType = userInfo.userType,
            whatToDoList = userInfo.whatToDoList,
            uid = userInfo.uid,
            token = userInfo.token,
            likes = userInfo.likes,
            cheers = userInfo.cheers,
        )
    }
}