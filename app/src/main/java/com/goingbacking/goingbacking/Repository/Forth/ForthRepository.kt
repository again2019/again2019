package com.goingbacking.goingbacking.Repository.Forth

import android.util.Log
import com.goingbacking.goingbacking.FCM.FirebaseTokenManager
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.Constants.Companion.CHEERS
import com.goingbacking.goingbacking.util.Constants.Companion.COUNT
import com.goingbacking.goingbacking.util.Constants.Companion.FAIL
import com.goingbacking.goingbacking.util.Constants.Companion.LIKES
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKMONTHINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKYEARINFO
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ForthRepository (
    val user : FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ) : ForthRepositoryIF {

    val myUid = user?.uid!!
    val cache = Source.CACHE

    override fun getSaveTimeMonthInfo(result: (UiState<ArrayList<NewSaveTimeMonthDTO>>)-> Unit) {
        val arrayList = ArrayList<NewSaveTimeMonthDTO>()

        firebaseFirestore.collection(RANKMONTHINFO).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).orderBy(COUNT, Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    arrayList.add(document.toObject(NewSaveTimeMonthDTO::class.java))
                }

                result.invoke(UiState.Success(arrayList))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(FAIL))
            }
    }

    override fun getSaveTimeYearInfo(result: (UiState<ArrayList<NewSaveTimeYearDTO>>)-> Unit) {
        val arrayList = ArrayList<NewSaveTimeYearDTO>()

        firebaseFirestore.collection(RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).orderBy(COUNT, Query.Direction.DESCENDING).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    arrayList.add(document.toObject(NewSaveTimeYearDTO::class.java))
                }

                result.invoke(UiState.Success(arrayList))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(FAIL))
            }
    }



    override fun getCheerInfo(destinationUid: String, result: (UiState<List<String>>) -> Unit) {
        firebaseFirestore.collection(RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(destinationUid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
//                    Log.d("experiment", document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers.toString())

                    result.invoke(UiState.Success(
                        document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers
                    ))
                } else {
                    result.invoke(UiState.Failure(FAIL))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(FAIL))
            }




    }

    override fun addCheerInfo(destinationUid: String, text: String, result: (UiState<List<String>>) -> Unit) {

        val tsDoc = firebaseFirestore.collection(RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(destinationUid)

        CoroutineScope(Dispatchers.IO).launch {
            val destinationInfo = firebaseFirestore.collection(USERINFO).document(destinationUid).get().await().toObject(UserInfoDTO::class.java)
            val userInfo = firebaseFirestore.collection(USERINFO).document(myUid).get(cache).await().toObject(UserInfoDTO::class.java)
            val cheer = myUid + ":" + userInfo!!.userNickName + ":" + text
            tsDoc.update(CHEERS, FieldValue.arrayUnion(cheer)).await()

            PushNotification(
                NotificationData(text, text),
                destinationInfo!!.token!!
            ).also {
                FirebaseTokenManager.sendNotification(it)
            }
            firebaseFirestore.collection(RANKYEARINFO).document(currentday("yyyy"))
                .collection(currentday("yyyy")).document(destinationUid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        //Log.d("experiment", document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers.toString())

                        result.invoke(UiState.Success(
                            document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers
                        ))
                    } else {
                        result.invoke(UiState.Failure(FAIL))
                    }
                }
                .addOnFailureListener {
                    result.invoke(UiState.Failure(FAIL))
                }.await()


        }


    }

    override fun deleteCheerInfo(
        destinationUid: String,
        text: String,
        result: (UiState<String>) -> Unit
    ) {
        val tsDoc = firebaseFirestore.collection(RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(destinationUid)
        CoroutineScope(Dispatchers.IO).launch {
            tsDoc.update(CHEERS, FieldValue.arrayRemove(text)).await()
        }
    }


}