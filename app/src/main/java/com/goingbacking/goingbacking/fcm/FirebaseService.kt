package com.goingbacking.goingbacking.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import androidx.core.app.*

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.data.api.NotificationAPI
import com.example.presentation.R
import com.example.presentation.ui.main.MainActivity
import com.goingbacking.goingbacking.fcm.FCMConstants.Companion.BASE_URL
import com.goingbacking.goingbacking.fcm.FirebaseTokenManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val CHANNEL_ID = "fcm_channel"


class FirebaseService : FirebaseMessagingService() {

    // 새로운 토큰으로 변경될 때
    // 앱을 삭제하거나 다른 기기에서 실행시킬 때
    override fun onNewToken(token: String) {

        FirebaseTokenManager.sendRegistrationToServer(applicationContext, token)

    }

    // 메시지를
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("experimenddd", message.data.toString())
        val intent = Intent(this, MainActivity::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = 223

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 11, intent, FLAG_MUTABLE)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setAllowSystemGeneratedContextualActions(true)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .setSmallIcon(R.mipmap.com_back_new)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(notificationID, notification)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel = NotificationChannel(CHANNEL_ID, channelName, IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }

    class RetrofitInstance {
        companion object {
            // Retrofit 객체 생성
            private val retrofit by lazy {
                Retrofit.Builder()
                    .baseUrl(BASE_URL) // 어떤 서버로 네트워크 통신을 요청할 것인지에 대한 설정
                    .addConverterFactory(GsonConverterFactory.create()) // 통신이 완료된 후, 어떤 Converter를 이용하여데이터 파싱
                    .build()
            }

            // Retrofit에서 NotificationAPI라는 api를 만든다
            val api by lazy {
                retrofit.create(NotificationAPI::class.java)
            }
        }
    }
}