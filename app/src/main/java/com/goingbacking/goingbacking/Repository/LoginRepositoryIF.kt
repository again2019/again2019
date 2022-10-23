package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

interface LoginRepositoryIF {

    // 구글 로그인
    fun getGSO(result: (UiState<GoogleSignInOptions>) -> Unit)

    // 이메일 로그인
    fun emailRegister(email: String, password: String, result: (UiState<String>) -> Unit)
    fun emailLogin(email: String, password: String, result: (UiState<String>) -> Unit)
    fun emailForgetPassword(email: String, result: (UiState<String>) -> Unit)
    fun signInWithCredential(token: String, result: (UiState<String>) -> Unit)
    fun getCurrentSession(result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
}