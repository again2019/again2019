package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Utils
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_first_input.*
import kotlinx.android.synthetic.main.activity_second_input.*

class InputRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
) :InputRepositoryIF {
    override fun addFirstInput(userInfoDTO: UserInfoDTO, result: (UiState<String>) -> Unit) {


        firebaseFirestore?.collection("UserInfo")?.document(user?.uid!!)
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

        firebaseFirestore?.collection("UserInfo")?.document(user?.uid!!)
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
        firebaseFirestore?.collection("UserInfo")?.document(user?.uid!!)
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


