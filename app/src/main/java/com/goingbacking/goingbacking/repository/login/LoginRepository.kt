package com.goingbacking.goingbacking.repository.login


import com.example.domain.util.UiState
import com.goingbacking.goingbacking.util.Constants.Companion.FAIL
import com.goingbacking.goingbacking.util.Constants.Companion.SUCCESS
import com.goingbacking.goingbacking.util.Constants.Companion.serverClientId
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class LoginRepository (
//    val firebaseAuth: FirebaseAuth,
//    val firebaseFirestore: FirebaseFirestore
) : LoginRepositoryIF {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user  = firebaseAuth.currentUser
    private val firebaseMessage = FirebaseMessaging.getInstance()
    override fun getGSO(result: (UiState<GoogleSignInOptions>) -> Unit) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(serverClientId)
            .requestEmail()
            .build()

            result.invoke(
                UiState.Success(gso)
            )

    }

    override fun emailRegister(email: String, password: String, result: (UiState<String>) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    result.invoke(
                        UiState.Success(SUCCESS)
                    )
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authentication")
                    } catch (e : FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure(FAIL))
                    } catch (e : FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure(FAIL))
                    } catch (e : FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure(FAIL))
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
                            UiState.Success(SUCCESS)
                        )
                    } else {
                        result.invoke(
                            UiState.Failure(FAIL)
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
                    result.invoke(UiState.Success(SUCCESS))
                } else {
                    result.invoke(UiState.Failure(FAIL))
                }
            }
    }

    override fun signInWithCredential(token: String, result: (UiState<String>) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result.invoke(UiState.Success(SUCCESS))
            } else {
                result.invoke(UiState.Success(FAIL))
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
            result.invoke(UiState.Failure(FAIL))
        } else {
            result.invoke(UiState.Success(
                currentUid
            ))
        }
    }




}