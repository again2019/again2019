package com.goingbacking.goingbacking.Repository


import android.util.Log
import com.goingbacking.goingbacking.util.UiState
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore

class LoginRepository (
    val firebaseAuth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore
) : LoginRepositoryIF {
    companion object {
        private const val serverClientId = "1036649010261-p08hat5d9stl7qvdun1mg4fv94kj8nt6.apps.googleusercontent.com"
        private const val success = "success"
        private const val fail = "fail"
    }


    override fun getGSO(result: (UiState<GoogleSignInOptions>) -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .build()

        if (gso != null) {
            result.invoke(
                UiState.Success(gso)
            )
        } else {
            result.invoke(
                UiState.Failure(fail)
            )
        }


    }

    override fun emailRegister(email: String, password: String, result: (UiState<String>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener {
                if(it.isSuccessful){
                    //Creating a user account
                    result.invoke(
                        UiState.Success(success)
                    )
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e : FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure(fail))
                    } catch (e : FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure(fail))
                    } catch (e : FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure(fail))
                    } catch (e : Exception) {
                        result.invoke(UiState.Failure(e.message))
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


    override fun emailLogin(email: String, password: String, result: (UiState<String>) -> Unit) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        result.invoke(
                            UiState.Success(success)
                        )
                    } else {
                        result.invoke(
                            UiState.Failure(fail)
                        )
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

    override fun emailForgetPassword(email: String, result: (UiState<String>) -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success(success))
                } else {
                    result.invoke(UiState.Failure(fail))
                }
            }
    }

    override fun signInWithCredential(token: String, result: (UiState<String>) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        firebaseAuth.signInWithCredential(credential)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result.invoke(UiState.Success(success))
            } else {
                result.invoke(UiState.Success(fail))
            }
        } .addOnFailureListener {
            result.invoke(UiState.Failure(
                it.localizedMessage
            ))
        }
    }

    override fun getCurrentSession(result: (UiState<String>) -> Unit) {
        val currentUid = firebaseAuth.currentUser?.uid
        if (currentUid == null) {
            result.invoke(UiState.Failure(fail))
        } else {
            result.invoke(UiState.Success(
                currentUid
            ))
        }
    }

    override fun logout(result: () -> Unit) {
        firebaseAuth.signOut()
        result.invoke()
    }


}