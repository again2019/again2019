package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

interface LoginRepositoryIF {

    fun getGSO(result: (UiState<GoogleSignInOptions>) -> Unit)

}