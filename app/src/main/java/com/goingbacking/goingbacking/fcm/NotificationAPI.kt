package com.goingbacking.goingbacking.fcm

import com.goingbacking.goingbacking.fcm.FCMConstants.Companion.CONTENT_TYPE
import com.goingbacking.goingbacking.fcm.FCMConstants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// REST API 명세에 맞는 Interface 선언
interface NotificationAPI {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")

    // 서버 통신은 비동기 처리이기 때문에 코루틴 사용을 위해 suspend 처리
    suspend fun postNotification(
        @Body notification: PushNotification
    ) : Response<ResponseBody>

}