package com.goingbacking.goingbacking.Repository

import android.util.Log
import android.widget.Toast
import com.goingbacking.goingbacking.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class LoginRepository (
    val firebaseAuth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore
) : LoginRepositoryIF {
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
                UiState.Failure("not found GSO")
            )
        }


    }

    override fun emailRegister(email: String, password: String, result: (UiState<String>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener {
                if(it.isSuccessful){
                    //Creating a user account
                    result.invoke(
                        UiState.Success("User register sucessful")
                    )
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e : FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
                        Log.d("experiment", "1")
                    } catch (e : FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
                        Log.d("experiment", "2")
                    } catch (e : FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, Email already registered"))
                        Log.d("experiment", "3")
                    } catch (e : Exception) {
                        result.invoke(UiState.Failure(e.message))
                        Log.d("experiment", "4")
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }

    }


    override fun emailLogin(result: (UiState<String>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun emailForgetPassword(result: (UiState<String>) -> Unit) {
        TODO("Not yet implemented")
    }


}