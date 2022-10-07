package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Utils
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_first_input.*

class InputRepository(
    val auth: FirebaseAuth,
    val firebaseFirestore: FirebaseFirestore

) :InputRepositoryIF {
    override fun addFirstInput(userInfoDTO: UserInfoDTO, result: (UiState<String>) -> Unit) {
        val userId = auth.currentUser?.uid

        firebaseFirestore?.collection("UserInfo")?.document(userId!!)?.set(userInfoDTO!!)
            .addOnSuccessListener {
                result.invoke(UiState.Success("UserInfo Success"))
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
//    fun storeToDB(userName : String): Boolean? {
//        var result :Boolean? =  null
//        var auth = FirebaseAuth.getInstance()
//        var firebaseFirestore = FirebaseFirestore.getInstance()
//        var userId = auth?.currentUser?.uid
//        var userInfoDTO = UserInfoDTO(userName, null, null, userId)
//        firebaseFirestore?.collection("UserInfo")?.document(userId!!)?.set(userInfoDTO!!).addOnSuccessListener {
//            result = true
//        }.addOnFailureListener {
//            result = false
//        }
//
//        return result
//    }


