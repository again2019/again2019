package com.goingbacking.goingbacking.fcm

import com.goingbacking.goingbacking.fcm.FCMConstants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
//    companion object {
//        // Retrofit 객체 생성
//        private val retrofit by lazy {
//            Retrofit.Builder()
//                .baseUrl(BASE_URL) // 어떤 서버로 네트워크 통신을 요청할 것인지에 대한 설정
//                .addConverterFactory(GsonConverterFactory.create()) // 통신이 완료된 후, 어떤 Converter를 이용하여데이터 파싱
//                .build()
//        }
//
//        // Retrofit에서 NotificationAPI라는 api를 만든다
//        val api by lazy {
//            retrofit.create(NotificationAPI::class.java)
//        }
//    }
}