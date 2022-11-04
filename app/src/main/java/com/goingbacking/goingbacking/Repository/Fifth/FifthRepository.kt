package com.goingbacking.goingbacking.Repository.Fifth

import com.goingbacking.goingbacking.AppConstants.Companion.SUCCESS
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FifthRepository(
    val user : FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth,
    val firebaseMessage : FirebaseMessaging
) : FifthRepositoryIF {

    val myUid = user?.uid!!
    val cache = Source.CACHE

    // 개인 정보 불러오는 코드
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

    // 정보 수정 저장
    override fun reviseUserInfo(nickname :String, type :String, selected : List<String>, result: (UiState<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            var token = ""
            CoroutineScope(Dispatchers.IO).launch {
                token = firebaseMessage.token.await()
                val userInfoDTO = UserInfoDTO(
                    uid = myUid,
                    userNickName = nickname,
                    userType = type,
                    whatToDoList = selected,
                    token = token
                )
                firebaseFirestore.collection(USERINFO).document(myUid).set(userInfoDTO).await()
            }
        }
    }

    override fun addInitWhatToDoMonthTime(
        whatToDoMonthDTO: WhatToDoMonthDTO,
        result: (UiState<String>) -> Unit
    ) {

        firebaseFirestore.collection(Constants.WHATTODOINFO).document(myUid)
            .collection(Constants.MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid+whatToDoMonthDTO.whatToDo)
            .set(whatToDoMonthDTO)
            .addOnSuccessListener {
                result.invoke(UiState.Success(Constants.SUCCESS))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(Constants.FAIL)
                )
            }
    }

    override fun addInitWhatToDoYearTime(
        whatToDoYearDTO: WhatToDoYearDTO,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(Constants.WHATTODOINFO).document(myUid)
            .collection(Constants.YEAR).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid+whatToDoYearDTO.whatToDo)
            .set(whatToDoYearDTO)
            .addOnSuccessListener {
                result.invoke(UiState.Success(Constants.SUCCESS))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(Constants.FAIL)
                )
            }

    }

}