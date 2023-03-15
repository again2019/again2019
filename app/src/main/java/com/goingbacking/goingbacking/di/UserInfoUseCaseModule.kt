package com.goingbacking.goingbacking.di

import com.example.domain.repository.NotificationRepository
import com.example.domain.repository.UserInfoRepository
import com.example.domain.usecase.userInfo.my.*
import com.example.domain.usecase.userInfo.other.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UserInfoUseCaseModule {

    // my

    @Provides
    fun provideAddMyUserInfoUseCase(
        userInfoRepository: UserInfoRepository
    ) : AddMyUserInfoUseCase {
        return AddMyUserInfoUseCase(userInfoRepository)
    }

    @Provides
    fun provideGetMyNickNameUseCase(
        userInfoRepository: UserInfoRepository
    ) : GetMyNickNameUseCase {
        return GetMyNickNameUseCase(userInfoRepository)
    }

    @Provides
    fun provideGetMyUserInfoUseCase(
        userInfoRepository: UserInfoRepository
    ) : GetMyUserInfoUseCase {
        return GetMyUserInfoUseCase(userInfoRepository)
    }


    @Provides
    fun provideUpdateMyUserSelectedListUseCase(
        userInfoRepository: UserInfoRepository
    ) : UpdateMyUserSelectedListUseCase {
        return UpdateMyUserSelectedListUseCase(userInfoRepository)
    }


    @Provides
    fun provideUpdateMyUserTypeUseCase(
        userInfoRepository: UserInfoRepository
    ) : UpdateMyUserTypeUseCase {
        return UpdateMyUserTypeUseCase(userInfoRepository)
    }

    @Provides
    fun provideUpdateUserInfoUseCase(
        userInfoRepository: UserInfoRepository
    ) : UpdateUserInfoUseCase {
        return UpdateUserInfoUseCase(userInfoRepository)
    }

    // other

    @Provides
    fun provideAddCheerUseCase(
        userInfoRepository: UserInfoRepository,
        notificationRepository: NotificationRepository,
    ) : AddCheerUseCase {
        return AddCheerUseCase(userInfoRepository, notificationRepository)
    }

    @Provides
    fun provideDeleteCheerUseCase(
        userInfoRepository: UserInfoRepository
    ) : DeleteCheerUseCase {
        return DeleteCheerUseCase(userInfoRepository)
    }

    @Provides
    fun provideGetCheerListUseCase(
        userInfoRepository: UserInfoRepository
    ) : GetCheerListUseCase {
        return GetCheerListUseCase(userInfoRepository)
    }

    @Provides
    fun provideGetOtherUserInfoUseCase(
        userInfoRepository: UserInfoRepository
    ) : GetOtherUserInfoUseCase {
        return GetOtherUserInfoUseCase(userInfoRepository)
    }

    @Provides
    fun provideUpdateLikeButtonUseCase(
        userInfoRepository: UserInfoRepository,
        notificationRepository: NotificationRepository,
    ) : UpdateLikeButtonUseCase {
        return UpdateLikeButtonUseCase(userInfoRepository, notificationRepository)
    }



}