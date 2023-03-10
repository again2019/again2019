package com.goingbacking.goingbacking.di

import com.goingbacking.goingbacking.fcm.FCMConstants
import com.goingbacking.goingbacking.fcm.NotificationAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(FCMConstants.BASE_URL) // 어떤 서버로 네트워크 통신을 요청할 것인지에 대한 설정
            .addConverterFactory(GsonConverterFactory.create()) // 통신이 완료된 후, 어떤 Converter를 이용하여데이터 파싱
            .build()
    }

    @Provides
    @Singleton
    fun provideNotificationAPI(retrofit : Retrofit) : NotificationAPI {
        return retrofit.create(NotificationAPI::class.java)
    }
}