package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class TmpTimeRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
) :TmpTimeRepositoryIF{

    override fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit) {
        firebaseFirestore?.collection(FBConstants.TMPTIMEINFO).document(user?.uid!!)
            .collection(user?.uid!!).get(Source.CACHE)
            .addOnSuccessListener {
                var tmpTimeDTOList : ArrayList<TmpTimeDTO> = arrayListOf()
                for(document in it){
                    tmpTimeDTOList.add(document.toObject(TmpTimeDTO::class.java)!!)
                }

                result.invoke(
                    UiState.Success(tmpTimeDTOList)
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

    override fun updateTmpTimeDayInfo(
        wakeUpTime: String,
        uidWakeUpTime: String,
        count: Double,
        result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit
    ) {
//        firebaseFirestore?.collection("SaveTimeInfo")?.document(userId!!)
//            ?.collection("Day")?.document(simpleDate2.format(tmpTimeDTOList[position].wakeUpTime).toString())
//            ?.collection(simpleDate3.format(tmpTimeDTOList[position].wakeUpTime).toString())?.document(userId!! + simpleDate3.format(tmpTimeDTOList[position].wakeUpTime).toString())
//            ?.update("count", FieldValue.increment(tmpTimeDTOList[position].nowSeconds?.toDouble()!!))
//
        TODO("Not yet implemented")

    }

    override fun updateTmpTimeMonthInfo(
        wakeUpTime: String,
        uidWakeUpTime: String,
        count: Double,
        result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun updateTmpTimeYearInfo(
        wakeUpTime: String,
        count: Double,
        result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}