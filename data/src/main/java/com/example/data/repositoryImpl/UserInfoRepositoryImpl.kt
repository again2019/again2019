package com.example.data.repositoryImpl

import com.example.data.dataSource.userInfoDataSource.UserInfoDataSource
import com.example.data.mapper.UserInfoMapper
import com.example.domain.model.UserInfoModel
import com.example.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoDataDataSource: UserInfoDataSource
) : UserInfoRepository {
    override suspend fun addUserInfoModel(userNickName: String) {
        userInfoDataDataSource.addUserInfoEntity(userNickName)
    }

    override suspend fun updateUserType(userType: String) {
        userInfoDataDataSource.updateUserType(userType)
    }

    override suspend fun updateUserSelectedList(selected: List<String>) {
        userInfoDataDataSource.updateUserSelectedList(selected)
    }

    override suspend fun getMyUserInfoModel(): UserInfoModel {
        return UserInfoMapper.mapperToUserInfoModel(userInfoDataDataSource.getMyUserInfoEntity())
    }

    override suspend fun getOtherUserInfoModel(destinationUid: String): UserInfoModel {
        return UserInfoMapper.mapperToUserInfoModel(userInfoDataDataSource.getOtherUserInfoEntity(destinationUid))
    }

    override suspend fun updateUserInfoModel(
        nickname: String,
        type: String,
        selected: List<String>
    ) {
        userInfoDataDataSource.updateUserInfoEntity(nickname, type, selected)
    }

    override suspend fun updateCheerList(destinationUid: String, cheer: String): List<String> {
        return userInfoDataDataSource.updateCheerList(destinationUid, cheer)
    }

    override suspend fun deleteCheer(destinationUid: String, cheer: String) {
        userInfoDataDataSource.deleteCheer(destinationUid, cheer)
    }

    override suspend fun updateLikeButton(destinationUid: String, state: String): String {
        return userInfoDataDataSource.updateLikeButton(destinationUid, state)
    }

}