package com.goingbacking.goingbacking.Repository.Forth

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RankRepository  (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
): RankRepositoryIF {
    val uid = user?.uid!!

    override fun getSecondSaveDayInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit) {
        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.DAY).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).get()
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
        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get()
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
        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection(FBConstants.MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).get()
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
        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection(FBConstants.YEAR).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get()
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