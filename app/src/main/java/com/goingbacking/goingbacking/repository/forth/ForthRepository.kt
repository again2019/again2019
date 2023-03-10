package com.goingbacking.goingbacking.repository.forth

import com.example.domain.util.UiState
import com.goingbacking.goingbacking.fcm.NotificationAPI
import com.goingbacking.goingbacking.fcm.NotificationData
import com.goingbacking.goingbacking.fcm.PushNotification
import com.goingbacking.goingbacking.model.*
import com.goingbacking.goingbacking.util.Constants.Companion.CHEERS
import com.goingbacking.goingbacking.util.Constants.Companion.COUNT
import com.goingbacking.goingbacking.util.Constants.Companion.FAIL
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKMONTHINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKYEARINFO
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class ForthRepository (
//    val user : FirebaseUser?,
//    val firebaseFirestore: FirebaseFirestore,
    val notificationAPI: NotificationAPI
        ) : ForthRepositoryIF {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user  = firebaseAuth.currentUser
    val myUid = user?.uid!!
    val cache = Source.CACHE

    /*
    ForthMainFragment1
     */

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

    /*
    ForthMainFragment2
    */
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


    /*
    CheerBottomSheet
     */

    // 응원 댓글 받아오는 코드
    override fun getCheerInfo(destinationUid: String, result: (UiState<List<String>>) -> Unit) {
        firebaseFirestore.collection(USERINFO).document(destinationUid)
            .get().addOnSuccessListener { document ->
                result.invoke(
                    UiState.Success(
                        document.toObject(UserInfoDTO::class.java)!!.cheers
                    )
                )
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(FAIL))
            }
    }

    // 응원 댓글 입력
    override fun addCheerInfo(destinationUid: String, text: String, result: (UiState<List<String>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            val tsDoc = firebaseFirestore.collection(USERINFO).document(destinationUid)
            val userInfo = firebaseFirestore.collection(USERINFO).document(myUid).get(cache).await().toObject(UserInfoDTO::class.java)
            val cheer = myUid + ":" + userInfo!!.userNickName + ":" + text
            tsDoc.update(CHEERS, FieldValue.arrayUnion(cheer)).await()
            val destinationInfo = tsDoc.get().await().toObject(UserInfoDTO::class.java)
            result.invoke(
                UiState.Success(
                    destinationInfo!!.cheers
                )
            )
            PushNotification(
                NotificationData("응원 메시지",  "응원 메시지를 확인해보세요!\n" + userInfo.userNickName + ": " + text),
                destinationInfo.token!!
            ).also {
                notificationAPI.postNotification(it)
            }
        }
    }

    // 응원 댓글 삭제
    override fun deleteCheerInfo(
        destinationUid: String,
        text: String,
        result: (UiState<String>) -> Unit
    ) {
        val tsDoc = firebaseFirestore.collection(USERINFO).document(destinationUid)
        CoroutineScope(Dispatchers.IO).launch {
            tsDoc.update(CHEERS, FieldValue.arrayRemove(text)).await()
        }
    }


}