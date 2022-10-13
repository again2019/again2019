package com.goingbacking.goingbacking.Repository

import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import java.text.SimpleDateFormat

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
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {

        firebaseFirestore?.collection("SaveTimeInfo")?.document(user?.uid!!)
            ?.collection("Day")?.document(wakeUpTime1)
            ?.collection(wakeUpTime2)?.document(user?.uid + wakeUpTime2)
            ?.update("count", count)
            .addOnSuccessListener {
                result.invoke(UiState.Success("SecondUpdate"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(
                    it.localizedMessage
                ))
            }
    }

    override fun updateTmpTimeMonthInfo(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore?.collection("SaveTimeInfo")?.document(user?.uid!!)
                    ?.collection("Month")?.document(wakeUpTime1)
                    ?.collection(wakeUpTime2)?.document(user?.uid!! + wakeUpTime2)
                    ?.update("count", count)

    }

    override fun updateTmpTimeYearInfo(
        wakeUpTime: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore?.collection("SaveTimeInfo")?.document(user?.uid!!)
                    ?.collection("Year")?.document(wakeUpTime)
                    ?.update("count", count)

    }

}