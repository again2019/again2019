package com.goingbacking.goingbacking.di.network

import com.example.data.api.NotificationAPI
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
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
            .baseUrl(FCMConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // 통신이 완료된 후, 어떤 Converter를 이용하여데이터 파싱
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNotificationAPI(retrofit: Retrofit) : NotificationAPI {
        return retrofit.create(NotificationAPI::class.java)
    }


}