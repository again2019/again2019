package com.goingbacking.goingbacking.Repository.Fifth

import com.goingbacking.goingbacking.AppConstants.Companion.SUCCESS
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class FifthRepository(
    val user : FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth

) : FifthRepositoryIF {

    val myUid = user?.uid!!
    val cache = Source.CACHE

    override fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit) {
        firebaseFirestore.collection(USERINFO).document(myUid)
            .get(cache)
            .addOnSuccessListener { document ->
                val data : UserInfoDTO? = document.toObject(UserInfoDTO::class.java)
                result.invoke(
                    UiState.Success(data!!)
                )
            }

            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun logout(result: (UiState<String>) -> Unit) {
        firebaseAuth.signOut()
        result.invoke(
            UiState.Success(SUCCESS)
        )
    }

    override fun signout(result: (UiState<String>) -> Unit) {
        firebaseAuth.currentUser!!.delete().addOnCompleteListener {
            if (it.isSuccessful) {
                result.invoke(UiState.Success(SUCCESS))

            }
        }
    }

}