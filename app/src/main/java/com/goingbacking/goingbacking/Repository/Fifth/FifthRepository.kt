package com.goingbacking.goingbacking.Repository.Fifth

import com.goingbacking.goingbacking.AppConstants.Companion.RANKMONTHINFO
import com.goingbacking.goingbacking.AppConstants.Companion.RANKYEARINFO
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

class FifthRepository() : FifthRepositoryIF {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user  = firebaseAuth.currentUser
    val myUid = user?.uid!!
    val cache = Source.CACHE


    /*
     FifthMainFragment
     */

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
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
                firebaseAuth.signOut()

                result.invoke(UiState.Success(SUCCESS))

            }
        }
    }

    /*
     ChangeInfoActivity
     */

    // 개인 정보 수정
    override fun reviseUserInfo(nickname :String, type :String, selected : List<String>, result: (UiState<String>) -> Unit) {
        val myDoc1 =  firebaseFirestore.collection(USERINFO).document(myUid)
        myDoc1.update("userNickName", nickname)
        myDoc1.update("userType", type)
        myDoc1.update("whatToDoList", selected)

        firebaseFirestore.collection(RANKMONTHINFO).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid)
            .update("nickname", nickname)

        firebaseFirestore.collection(RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid)
            .update("nickname", nickname)
    }

    // month whattodo chart를 위해 초기화하는 코드
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

    // year whattodo chart를 위해 초기화하는 코드
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