package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginRepository : LoginRepositoryIF {
    override fun getGSO(result: (UiState<GoogleSignInOptions>) -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("1036649010261-p08hat5d9stl7qvdun1mg4fv94kj8nt6.apps.googleusercontent.com")
            .requestEmail()
            .build()

        if (gso != null) {
            result.invoke(
                UiState.Success(gso)
            )
        } else {
            result.invoke(
                UiState.Failure("not found gso")
            )
        }


    }


}