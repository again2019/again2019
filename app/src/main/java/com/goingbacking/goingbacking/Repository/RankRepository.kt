package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RankRepository  (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
): RankRepositoryIF {
    val uid = user?.uid!!

    override fun getSecondSaveDayInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit) {
        val current = LocalDateTime.now()
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy-MM")
        val curYearMonth = current.format(simpleDate1)



        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.DAY).document(curYearMonth)
            .collection(curYearMonth).get()
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





    override fun getSecondSaveMonthInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit) {
        val current = LocalDateTime.now()
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy")

        val curYear = current.format(simpleDate1)

        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.MONTH).document(curYear)
            .collection(curYear).get()
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

    override fun getSecondSaveYearInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit) {

        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.YEAR).get()
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

    override fun getSecondWhatToDoMonthInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit) {
        val now = LocalDate.now()
        val Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))

        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection(FBConstants.MONTH).document(Strnow)
            .collection(Strnow).get()
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

    override fun getSecondWhatToDoYearInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)  {
        val now = LocalDate.now()
        val Strnow = now.format(DateTimeFormatter.ofPattern("yyyy"))

        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection(FBConstants.YEAR).document(Strnow)
            .collection(Strnow).get()
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