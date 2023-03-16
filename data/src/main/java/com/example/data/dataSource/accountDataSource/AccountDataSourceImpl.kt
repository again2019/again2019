package com.example.data.dataSource.accountDataSource

import com.example.domain.util.Constants.Companion.serverClientId
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*

class AccountDataSourceImpl(
    private val firebaseUser: FirebaseUser?,
    private val firebaseAuth : FirebaseAuth,
) : AccountDataSource {
    override suspend fun getGso(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .build()
    }

    override suspend fun signInWithCredential(token: String) {
        firebaseAuth.signInWithCredential(
            GoogleAuthProvider.getCredential(token, null)
        )
    }

    override suspend fun registerEmail(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun loginEmail(email: String, password: String)  {
        firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun findEmailPassword(email: String)  {
        firebaseAuth.sendPasswordResetEmail(email)
    }

    override suspend fun getCurrentSession() : String? {
        return firebaseUser?.uid
    }

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }

    override suspend fun signOut() {
        firebaseUser!!.delete()
    }
}