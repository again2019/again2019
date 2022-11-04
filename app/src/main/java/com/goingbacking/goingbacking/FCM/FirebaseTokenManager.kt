package com.goingbacking.goingbacking.FCM

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "experiment"

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


    fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(!response.isSuccessful) {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }



}