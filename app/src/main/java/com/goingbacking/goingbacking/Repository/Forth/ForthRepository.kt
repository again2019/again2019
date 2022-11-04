package com.goingbacking.goingbacking.Repository.Forth

import android.util.Log
import com.goingbacking.goingbacking.FCM.FirebaseTokenManager
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.Constants.Companion.FAIL
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKMONTHINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKYEARINFO
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
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
    companion object {
        private const val COUNT = "count"
    }


    override fun getSaveTimeMonthInfo(result: (UiState<ArrayList<NewSaveTimeMonthDTO>>)-> Unit) {
        var current = LocalDateTime.now()
        current = current.minusDays(10)
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

    // 달별
    override fun likeButtonInfo(destinationUid :String, state :String, result: (UiState<String>) -> Unit) {
        var current = LocalDateTime.now()
        current = current.minusDays(10)
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy-MM")
        val simpleDate2 = DateTimeFormatter.ofPattern("yyyy")

        val curYearMonth = current.format(simpleDate1)
        val curYear = current.format(simpleDate2)


        val tsDoc1 = firebaseFirestore.collection(RANKMONTHINFO).document(curYearMonth)
            .collection(curYearMonth).document(destinationUid)
        val tsDoc2 = firebaseFirestore.collection(RANKYEARINFO).document(curYear)
            .collection(curYear).document(destinationUid)
        if (state.equals("plus")) {
            tsDoc1.update("likes", FieldValue.arrayUnion(myUid))
            tsDoc2.update("likes", FieldValue.arrayUnion(myUid))

        } else {
            tsDoc1.update("likes", FieldValue.arrayRemove(myUid))
            tsDoc2.update("likes", FieldValue.arrayUnion(myUid))

        }
    }

    override fun getCheerInfo(destinationUid: String, result: (UiState<List<String>>) -> Unit) {
        var current = LocalDateTime.now()
        current = current.minusDays(10)
        val simpleDate = DateTimeFormatter.ofPattern("yyyy")
        val curYear = current.format(simpleDate)

        firebaseFirestore.collection(RANKYEARINFO).document(curYear)
            .collection(curYear).document(destinationUid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("experiment", document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers.toString())

                    result.invoke(UiState.Success(
                        document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers
                    ))
                } else {
                    result.invoke(UiState.Failure(
                        "fail"
                    ))
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(
                    "fail"
                ))
            }




    }

    override fun addCheerInfo(destinationUid: String, text: String, result: (UiState<List<String>>) -> Unit) {
        var current = LocalDateTime.now()
        current = current.minusDays(10)
        val simpleDate = DateTimeFormatter.ofPattern("yyyy")
        val curYear = current.format(simpleDate)

        val tsDoc = firebaseFirestore.collection(RANKYEARINFO).document(curYear)
            .collection(curYear).document(destinationUid)

        CoroutineScope(Dispatchers.IO).launch {
            val destinationInfo = firebaseFirestore.collection(USERINFO).document(destinationUid).get().await().toObject(UserInfoDTO::class.java)
            Log.d("experiment", destinationInfo.toString())

            val userInfo = firebaseFirestore.collection(USERINFO).document(myUid).get(cache).await().toObject(UserInfoDTO::class.java)
            Log.d("experiment", userInfo.toString())

            val cheer = myUid + ":" + userInfo!!.userNickName + ":" + text
//
//            Log.d("experiment", cheer.toString())
            tsDoc.update("cheers", FieldValue.arrayUnion(cheer)).await()
            Log.d("experiment", "ss")

            PushNotification(
                NotificationData("title", text),
                destinationInfo!!.token!!
            ).also {
                FirebaseTokenManager.sendNotification(it)
            }
            firebaseFirestore.collection(RANKYEARINFO).document(curYear)
                .collection(curYear).document(destinationUid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("experiment", document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers.toString())

                        result.invoke(UiState.Success(
                            document.toObject(NewSaveTimeYearDTO::class.java)!!.cheers
                        ))
                    } else {
                        result.invoke(UiState.Failure(
                            "fail"
                        ))
                    }
                }
                .addOnFailureListener {
                    result.invoke(UiState.Failure(
                        "fail"
                    ))
                }.await()


        }


    }

    override fun deleteCheerInfo(
        destinationUid: String,
        text: String,
        result: (UiState<String>) -> Unit
    ) {
        var current = LocalDateTime.now()
        current = current.minusDays(10)
        val simpleDate = DateTimeFormatter.ofPattern("yyyy")
        val curYear = current.format(simpleDate)


        val tsDoc = firebaseFirestore.collection(RANKYEARINFO).document(curYear)
            .collection(curYear).document(destinationUid)
        CoroutineScope(Dispatchers.IO).launch {
            tsDoc.update("cheers", FieldValue.arrayRemove(text)).await()
        }
    }


}