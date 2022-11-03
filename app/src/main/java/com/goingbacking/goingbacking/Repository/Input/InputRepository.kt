package com.goingbacking.goingbacking.Repository.Input

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.util.Constants.Companion.FAIL
import com.goingbacking.goingbacking.util.Constants.Companion.MONTH
import com.goingbacking.goingbacking.util.Constants.Companion.SUCCESS
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.Constants.Companion.USERTYPE
import com.goingbacking.goingbacking.util.Constants.Companion.WHATTODOINFO
import com.goingbacking.goingbacking.util.Constants.Companion.WHATTODOLIST
import com.goingbacking.goingbacking.util.Constants.Companion.YEAR
import com.goingbacking.goingbacking.util.Constants.Companion.YYYYMM
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InputRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
) : InputRepositoryIF {

    val myUid = user?.uid!!
    val cache = Source.CACHE

    // --- FirstInputFragment ---
    override fun addFirstInput(userNickName: String, result: (UiState<String>) -> Unit) {

        val userInfoDTO = UserInfoDTO(
            uid = myUid,
            userNickName = userNickName
        )

        CoroutineScope(Dispatchers.IO).launch {
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

    override fun checkInput(result: (UiState<UserInfoDTO>) -> Unit) {
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

    override fun addInitWhatToDoMonthTime(
        whatToDoMonthDTO: WhatToDoMonthDTO,
        result: (UiState<String>) -> Unit
    ) {
        val now = LocalDate.now()
        val Strnow = now.format(DateTimeFormatter.ofPattern(YYYYMM))

        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection(MONTH).document(Strnow)
            .collection(Strnow).document(myUid+whatToDoMonthDTO.whatToDo)
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
        val now = LocalDate.now()
        val Strnow = now.format(DateTimeFormatter.ofPattern("yyyy"))

        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection(YEAR).document(Strnow)
            .collection(Strnow).document(myUid+whatToDoYearDTO.whatToDo)
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
}


