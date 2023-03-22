package com.example.data.api


import com.example.data.entity.NotificationEntity
import com.example.domain.util.FCMConstants.Companion.CONTENT_TYPE
import com.example.domain.util.FCMConstants.Companion.SERVER_KEY
import com.skydoves.sandwich.ApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// REST API 명세에 맞는 Interface 선언
interface NotificationAPI {

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")

    suspend fun postNotification(
        @Body notification: NotificationEntity
    ) : ApiResponse<ResponseBody>

}