package com.goingbacking.goingbacking.FCM

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseTokenManager {


    /**
     * 서버에 토큰값을 저장합니다.
     * document = 디바이스 고유 ID ( UniqueID )
     */
    @SuppressLint("HardwareIds")
    fun sendRegistrationToServer(context: Context, token: String) {
        FirebaseFirestore.getInstance().collection("Token")
            .document(
                Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            )
            .set(mapOf("token" to token))
    }

}