package com.example.data.repositoryImpl.First

import com.example.data.dataSource.UserInfoDataSource
import com.example.data.mapper.UserInfoMapper
import com.example.domain.model.UserInfoModel
import com.example.domain.repository.UserInfoRepository
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoDataDataSource: UserInfoDataSource
) : UserInfoRepository {
    override suspend fun getUserInfoModel(): UserInfoModel {
        return UserInfoMapper.mapperToUserInfoModel(userInfoDataDataSource.getUserInfoEntity())
    }

    override suspend fun updateUserInfoModel(
        nickname: String,
        type: String,
        selected: List<String>
    ) {
        userInfoDataDataSource.updateUserInfoEntity(nickname, type, selected)
    }
}