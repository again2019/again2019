package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.WHATTODOINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.YEAR
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InputRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
) :InputRepositoryIF {

    val myUid = user?.uid!!
    val cache = Source.CACHE

    override fun addFirstInput(userInfoDTO: UserInfoDTO, result: (UiState<String>) -> Unit) {
        firebaseFirestore?.collection(USERINFO)?.document(myUid)
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

        firebaseFirestore?.collection(USERINFO)?.document(myUid)
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
        firebaseFirestore?.collection(USERINFO)?.document(myUid)
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

    override fun checkInput(result: (UiState<UserInfoDTO>) -> Unit) {
        firebaseFirestore?.collection(USERINFO)?.document(myUid)
            ?.get(cache)
            ?.addOnSuccessListener { document ->
                val userInfo = document.toObject(UserInfoDTO::class.java)

                result.invoke(
                    UiState.Success(userInfo!!)
                )
            }
            ?.addOnFailureListener {
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
        var now = LocalDate.now()
        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))

        firebaseFirestore?.collection(WHATTODOINFO).document(myUid)
            .collection(MONTH).document(Strnow)
            .collection(Strnow).document(myUid+whatToDoMonthDTO.whatToDo)
            .set(whatToDoMonthDTO)
            .addOnSuccessListener {
                result.invoke(UiState.Success("success"))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure("fail")
                )
            }
    }

    override fun addInitWhatToDoYearTime(
        whatToDoYearDTO: WhatToDoYearDTO,
        result: (UiState<String>) -> Unit
    ) {
        var now = LocalDate.now()
        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy"))

        firebaseFirestore?.collection(WHATTODOINFO).document(myUid)
            .collection(YEAR).document(Strnow)
            .collection(Strnow).document(myUid+whatToDoYearDTO.whatToDo)
            .set(whatToDoYearDTO)
            .addOnSuccessListener {
                result.invoke(UiState.Success("success"))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure("fail")
                )
            }

    }


}


