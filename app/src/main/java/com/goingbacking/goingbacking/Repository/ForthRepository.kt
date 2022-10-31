package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKMONTHINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKYEARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ForthRepository (
    val user : FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ) : ForthRepositoryIF {

    val myUid = user?.uid!!

    companion object {
        private const val FAIL = "fail"
        private const val COUNT = "count"
    }


    override fun getSaveTimeMonthInfo(result: (UiState<ArrayList<NewSaveTimeMonthDTO>>)-> Unit) {
        var current = LocalDateTime.now()
        current = current.minusDays(1)
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy-MM")
        val curYearMonth = current.format(simpleDate1)

        val arrayList = ArrayList<NewSaveTimeMonthDTO>()

        firebaseFirestore.collection(RANKMONTHINFO).document(curYearMonth)
            .collection(curYearMonth).orderBy(COUNT, Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    arrayList.add(document.toObject(NewSaveTimeMonthDTO::class.java))
                }

                result.invoke(UiState.Success(arrayList))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(FAIL))
            }
    }

    override fun getSaveTimeYearInfo(result: (UiState<ArrayList<NewSaveTimeYearDTO>>)-> Unit) {
        val current = LocalDateTime.now()
        val simpleDate = DateTimeFormatter.ofPattern("yyyy")
        val curYear = current.format(simpleDate)

        val arrayList = ArrayList<NewSaveTimeYearDTO>()

        firebaseFirestore.collection(RANKYEARINFO).document(curYear)
            .collection(curYear).orderBy(COUNT, Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    arrayList.add(document.toObject(NewSaveTimeYearDTO::class.java))
                }

                result.invoke(UiState.Success(arrayList))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(FAIL))
            }
    }

    override fun likeButtonInfo() {
        TODO("Not yet implemented")
    }


}