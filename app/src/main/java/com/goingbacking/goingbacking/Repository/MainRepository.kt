package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.FBConstants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.DATE
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class MainRepository (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ): MainRepositoryIF {

    override fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit) {
        firebaseFirestore.collection(USERINFO)?.document(user?.uid!!)
            ?.get(Source.CACHE)
            ?.addOnSuccessListener { document ->
                val data :UserInfoDTO? = document.toObject(UserInfoDTO::class.java)
                result.invoke(
                    UiState.Success(data!!)
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

    override fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit) {
        firebaseFirestore?.collection(CALENDARINFO)?.document(user?.uid!!)
            ?.collection(path1)?.document(path2)
            ?.set(event)
            .addOnSuccessListener {
                result.invoke(UiState.Success("ScheduleInput Success"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun addDateInfo(date: DateDTO, result: (UiState<String>) -> Unit) {
        firebaseFirestore?.collection(DATE)?.document(user?.uid!!)?.set(date)
            ?.addOnSuccessListener {
                result.invoke(UiState.Success("DateInfo Success"))
            }
            ?.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }

    }

    override fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit) {
        firebaseFirestore?.collection("Date")?.document(user?.uid!!)
            ?.get(Source.CACHE)
            ?.addOnSuccessListener { document ->
                val data: DateDTO? = document.toObject(DateDTO::class.java)
                result.invoke(
                    UiState.Success(data!!)
                )
            }
            ?.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun getThirdDateInfo2(result: (UiState<DateDTO>) -> Unit) {
        firebaseFirestore?.collection("Date")?.document(user?.uid!!)
            ?.get(Source.CACHE)
            ?.addOnSuccessListener { document ->
                val data: DateDTO? = document.toObject(DateDTO::class.java)



                result.invoke(
                    UiState.Success(data!!)
                )
            }
            ?.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }


}