package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class InputRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
) :InputRepositoryIF {
    override fun addFirstInput(userInfoDTO: UserInfoDTO, result: (UiState<String>) -> Unit) {


        firebaseFirestore?.collection(USERINFO)?.document(user?.uid!!)
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

        firebaseFirestore?.collection(USERINFO)?.document(user?.uid!!)
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

    override fun updateThirdInput(whatToDo: String, result: (UiState<String>) -> Unit) {
        firebaseFirestore?.collection(USERINFO)?.document(user?.uid!!)
            ?.update("whatToDo",whatToDo)
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


