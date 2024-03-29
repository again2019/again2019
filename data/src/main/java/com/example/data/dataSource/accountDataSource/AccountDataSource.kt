package com.example.data.dataSource.accountDataSource

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

interface AccountDataSource {

    // google login
    suspend fun getGso() : GoogleSignInOptions

    suspend fun signInWithCredential(token: String)

    // email login
    suspend fun registerEmail(email: String, password: String)

    suspend fun loginEmail(email: String, password: String)

    suspend fun findEmailPassword(email: String)

    // current state
    suspend fun getCurrentSession() : String?

    // logout
    suspend fun logOut()

    // sign out
    suspend fun signOut()
}