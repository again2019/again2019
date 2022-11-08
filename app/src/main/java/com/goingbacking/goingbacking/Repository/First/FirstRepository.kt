package com.goingbacking.goingbacking.Repository.First

import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.Constants.Companion.SUCCESS
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.FBConstants.Companion.DAY
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.WHATTODOINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.YEAR
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FirstRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
) : FirstRepositoryIF {
    val myUid = user?.uid!!
    val cache = Source.CACHE

    override fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit) {
        firebaseFirestore.collection(FBConstants.TMPTIMEINFO).document(myUid)
            .collection(myUid).get(cache)
            .addOnSuccessListener {
                val tmpTimeDTOList : ArrayList<TmpTimeDTO> = arrayListOf()
                for(document in it){
                    tmpTimeDTOList.add(document.toObject(TmpTimeDTO::class.java))
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

        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(DAY).document(wakeUpTime1)
            .collection(wakeUpTime1).document(myUid + wakeUpTime2)
            .update("count", count)
            .addOnSuccessListener {
                result.invoke(UiState.Success(SUCCESS))
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
        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                    .collection(MONTH).document(wakeUpTime1)
                    .collection(wakeUpTime1).document(myUid + wakeUpTime2)
                    .update("count", count)

    }

    override fun updateTmpTimeYearInfo(
        wakeUpTime: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                    .collection(YEAR).document(wakeUpTime)
                    .update("count", count)

    }

    override fun updateWhatToDoMonthInfo(
        whatToDo: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection(MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid + whatToDo)
            .update("count", count)

    }

    override fun updateWhatToDoYearInfo(
        whatToDo: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection(YEAR).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid + whatToDo)
            .update("count", count)

    }

    override fun getWhatToDoInfo(result: (UiState<String>) -> Unit) {
        firebaseFirestore.collection(FBConstants.USERINFO).document(myUid)
            .get()
            .addOnSuccessListener { document ->
                val data :UserInfoDTO? = document.toObject(UserInfoDTO::class.java)
                result.invoke(
                    UiState.Success(data?.whatToDoList.toString())
                )
            }

            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }    }


}