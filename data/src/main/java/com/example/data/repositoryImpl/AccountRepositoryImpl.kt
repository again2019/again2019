package com.example.data.repositoryImpl

import com.example.data.dataSource.accountDataSource.AccountDataSource
import com.example.domain.repository.AccountRepository
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDataSource: AccountDataSource
) : AccountRepository {
    override suspend fun getGso(): GoogleSignInOptions {
        return accountDataSource.getGso()
    }

    override suspend fun signInWithCredential(token: String) {
        accountDataSource.signInWithCredential(token)
    }

    override suspend fun registerEmail(email: String, password: String) {
        accountDataSource.registerEmail(email, password)
    }

    override suspend fun loginEmail(email: String, password: String) {
        accountDataSource.loginEmail(email, password)
    }

    override suspend fun findEmailPassword(email: String) {
        accountDataSource.findEmailPassword(email)
    }

    override suspend fun getCurrentSession(): String? {
        return accountDataSource.getCurrentSession()
    }

    override suspend fun logOut() {
        accountDataSource.logOut()
    }

    override suspend fun signOut() {
        accountDataSource.signOut()
    }
}