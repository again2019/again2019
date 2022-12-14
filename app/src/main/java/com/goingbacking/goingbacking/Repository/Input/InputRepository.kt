package com.goingbacking.goingbacking.Repository.Input

import android.util.Log
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.Constants.Companion.FAIL
import com.goingbacking.goingbacking.util.Constants.Companion.MONTH
import com.goingbacking.goingbacking.util.Constants.Companion.SUCCESS
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.Constants.Companion.USERTYPE
import com.goingbacking.goingbacking.util.Constants.Companion.WHATTODOINFO
import com.goingbacking.goingbacking.util.Constants.Companion.WHATTODOLIST
import com.goingbacking.goingbacking.util.Constants.Companion.YEAR
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class InputRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore,
    val firebaseMessage : FirebaseMessaging
) : InputRepositoryIF {

    val myUid = user?.uid!!
    val cache = Source.CACHE

    // --- FirstInputFragment ---
    override fun addFirstInput(userNickName: String, result: (UiState<String>) -> Unit) {

        var token = ""
        CoroutineScope(Dispatchers.IO).launch {
            token = firebaseMessage.token.await()
            val userInfoDTO = UserInfoDTO(
                uid = myUid,
                userNickName = userNickName,
                token = token
            )
            firebaseFirestore.collection(USERINFO).document(myUid).set(userInfoDTO).await()
        }
    }

    // --- SecondInputFragment ---
    override fun updateSecondInput(userType: String, result: (UiState<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseFirestore.collection(USERINFO).document(myUid)
                .update(USERTYPE, userType).await()
        }
    }

    // --- ThirdInputFragment ---
    override fun updateThirdInput(selected : List<String>, result: (UiState<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseFirestore.collection(USERINFO).document(myUid)
                .update(WHATTODOLIST, selected)
                .addOnSuccessListener {
                    result.invoke(UiState.Success(SUCCESS))
                }
                .addOnFailureListener {
                    result.invoke(
                        UiState.Failure(
                            it.localizedMessage
                        )
                    )
                }.await()
        }
    }

    // --- InputBottomSheet ---
    override fun checkInput(result: (UiState<UserInfoDTO>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseFirestore.collection(USERINFO).document(myUid)
                .get(cache)
                .addOnSuccessListener { document ->
                    val userInfo = document.toObject(UserInfoDTO::class.java)

                    result.invoke(
                        UiState.Success(userInfo!!)
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

    }

    override fun addInitWhatToDoMonthTime(
        whatToDoMonthDTO: WhatToDoMonthDTO,
        result: (UiState<String>) -> Unit
    ) {

        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection(MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid+whatToDoMonthDTO.whatToDo)
            .set(whatToDoMonthDTO)
            .addOnSuccessListener {
                result.invoke(UiState.Success(SUCCESS))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(FAIL)
                )
            }
    }

    override fun addInitWhatToDoYearTime(
        whatToDoYearDTO: WhatToDoYearDTO,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection(YEAR).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid+whatToDoYearDTO.whatToDo)
            .set(whatToDoYearDTO)
            .addOnSuccessListener {
                result.invoke(UiState.Success(SUCCESS))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(FAIL)
                )
            }

    }

    override fun addInitRankMonthTime(
        rankMonthDTO: NewSaveTimeMonthDTO,
        result: (UiState<String>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseFirestore.collection(AppConstants.RANKMONTHINFO).document(currentday("yyyy-MM"))
                .collection(currentday("yyyy-MM")).document(myUid)
                .set(rankMonthDTO).await()
        }
    }

    override fun addInitRankYearTime(
        rankYearDTO: NewSaveTimeYearDTO,
        result: (UiState<String>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseFirestore.collection(AppConstants.RANKYEARINFO).document(currentday("yyyy"))
                .collection(currentday("yyyy")).document(myUid)
                .set(rankYearDTO).await()
        }
    }
}


