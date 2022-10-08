package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Utils
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_first_input.*
import kotlinx.android.synthetic.main.activity_second_input.*

class InputRepository(
    val auth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore
) :InputRepositoryIF {
    override fun addFirstInput(userInfoDTO: UserInfoDTO, result: (UiState<String>) -> Unit) {
        val userId = auth.currentUser?.uid

        firebaseFirestore?.collection("UserInfo")?.document(userId!!)
            ?.set(userInfoDTO!!)
            .addOnSuccessListener {
                result.invoke(UiState.Success("FirstInput Success"))
            }

            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )

            }



    }

    override fun updateSecondInput(userType: String, result: (UiState<String>) -> Unit) {
        val userId = auth.currentUser?.uid

        firebaseFirestore?.collection("UserInfo")?.document(userId!!)
            ?.update("userType", userType)

            .addOnSuccessListener {
                result.invoke(UiState.Success("SecondUpdate"))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }

    }


}


