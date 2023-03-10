package com.goingbacking.goingbacking.repository.second

import com.example.domain.util.UiState
import com.goingbacking.goingbacking.model.*
import com.goingbacking.goingbacking.util.FBConstants.Companion.DAY
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.WHATTODOINFO
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.messaging.FirebaseMessaging

class SecondRepository (
//    val user: FirebaseUser?,
//    val firebaseFirestore: FirebaseFirestore
        ): SecondRepositoryIF {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user  = firebaseAuth.currentUser
    private val firebaseMessage = FirebaseMessaging.getInstance()
    val uid = user?.uid!!
    val cache = Source.CACHE
    /*
    SecondMainFragment1
     */

    // 매일 통계를 보여주는 코드
    override fun getSecondSaveDayInfo(result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit) {
        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            .collection(DAY).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).get(cache)
            .addOnSuccessListener {
                val saveTimeDayDTOList = arrayListOf<SaveTimeDayDTO>()

                for (document in it) {
                    saveTimeDayDTOList.add(document.toObject(SaveTimeDayDTO::class.java))
                }

                result.invoke(
                    UiState.Success(saveTimeDayDTOList)
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


    // 달 통계를 보여주는 코드
    override fun getSecondSaveMonthInfo(result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit) {
        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            .collection(MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache)
            .addOnSuccessListener {
                val saveTimeMonthDTOList = arrayListOf<SaveTimeMonthDTO>()

                for (document in it) {
                    saveTimeMonthDTOList.add(document.toObject(SaveTimeMonthDTO::class.java))
                }
                result.invoke(
                    UiState.Success(saveTimeMonthDTOList)
                )
            }
    }

    // 연도 통계를 보여주는 코드
    override fun getSecondSaveYearInfo(result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit) {

        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            .collection("year").get(cache)
            .addOnSuccessListener {
                val saveTimeYearDTOList = arrayListOf<SaveTimeYearDTO>()
                for(document in it){
                    saveTimeYearDTOList.add(document.toObject(SaveTimeYearDTO::class.java))
                }

                result.invoke(
                    UiState.Success(saveTimeYearDTOList)
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


    /*
    SecondMainFragment2
     */

    // 어떤 자기계발을 했는지 달 통계를 보여주는 코드
    override fun getSecondWhatToDoMonthInfo(result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit) {
        firebaseFirestore.collection(WHATTODOINFO).document(uid)
            .collection(MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).get(cache)
            .addOnSuccessListener {
                val whatToDoMonthDTOList = arrayListOf<WhatToDoMonthDTO>()
                for (document in it) {
                    whatToDoMonthDTOList.add(document.toObject(WhatToDoMonthDTO::class.java))
                }

                result.invoke(
                    UiState.Success(whatToDoMonthDTOList)
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

    // 어떤 자기계발을 했는지 연도 통계를 보여주는 코드
    override fun getSecondWhatToDoYearInfo(result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)  {
        firebaseFirestore.collection(WHATTODOINFO).document(uid)
            .collection("year").document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache)
            .addOnSuccessListener {
                val whatToDoYearDTOList = arrayListOf<WhatToDoYearDTO>()
                for (document in it) {
                    whatToDoYearDTOList.add(document.toObject(WhatToDoYearDTO::class.java))
                }

                result.invoke(
                    UiState.Success(whatToDoYearDTOList)
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


