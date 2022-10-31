package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.*
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
    override fun getUserInfo(result: Result<ArrayList<UserInfo>>) {
        TODO("Not yet implemented")
    }

    override fun getSaveTimeMonthInfo(result: (UiState<ArrayList<NewSaveTimeMonthDTO>>)-> Unit) {
        val current = LocalDateTime.now()
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy-MM")
        val curYearMonth = current.format(simpleDate1)

        val arrayList = ArrayList<NewSaveTimeMonthDTO>()

        firebaseFirestore.collection("RankMonthInfo").document(curYearMonth)
            .collection(curYearMonth).orderBy("count", Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    arrayList.add(document.toObject(NewSaveTimeMonthDTO::class.java))
                }

                result.invoke(UiState.Success(arrayList))
            }
    }

    override fun getSaveTimeYearInfo(result: (UiState<ArrayList<NewSaveTimeYearDTO>>)-> Unit) {
        TODO("Not yet implemented")
    }


}