package com.goingbacking.goingbacking.Repository.Forth

import com.goingbacking.goingbacking.FCM.FirebaseTokenManager
import com.goingbacking.goingbacking.FCM.NotificationAPI
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RankRepository  (
//    val user: FirebaseUser?,
//    val firebaseFirestore: FirebaseFirestore,
    val notificationAPI: NotificationAPI
): RankRepositoryIF {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user  = firebaseAuth.currentUser
    val uid = user?.uid!!

    /*
    RankActivity1
     */

    // 일별 통계 받아오는 코드
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

    // 달별 자기계발 통계 받아오는 코드
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

    /*
     RankActivity2
      */

    // 달별 통계 받아오는 코드
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

    // 연도별 자기계발 통계 받아오는 코드
    override fun getSecondWhatToDoYearInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)  {
        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection("year").document(currentday("yyyy"))
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


    /*
    RankActivity1
    RankActivity2
     */


    // 개인정보 받아오는 코드
    override fun getFifthUserInfo(destinationUid: String, result: (UiState<UserInfoDTO>) -> Unit) {
        firebaseFirestore.collection(Constants.USERINFO).document(destinationUid)
            .get()
            .addOnSuccessListener { document ->
                val data : UserInfoDTO? = document.toObject(UserInfoDTO::class.java)
                result.invoke(
                    UiState.Success(data!!)
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

    // 좋아요 버튼 기능 month, year
    override fun likeButtonInfo(destinationUid :String, state :String, result: (UiState<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val tsDoc1 = firebaseFirestore.collection(Constants.USERINFO).document(destinationUid)
            if (state.equals("plus")) {
                tsDoc1.update(Constants.LIKES, FieldValue.arrayUnion(uid)).await()
                tsDoc1.get().addOnSuccessListener {
                    val likeCount = it.toObject(UserInfoDTO::class.java)!!.likes.size


                    PushNotification(
                        NotificationData("좋아요", it.toObject(UserInfoDTO::class.java)!!.userNickName!! + "님의 좋아요 수가 늘었습니다! 확인해보세요!"),
                        it.toObject(UserInfoDTO::class.java)!!.token!!
                    ).also {
                        CoroutineScope(Dispatchers.IO).launch {
                            notificationAPI.postNotification(it)
                        }
                    }

                    result.invoke(
                        UiState.Success(
                            likeCount.toString()
                        )
                    )
                }.addOnFailureListener {
                    result.invoke(
                        UiState.Failure(
                            it.localizedMessage
                        )
                    )
                }.await()
            } else {
                tsDoc1.update(Constants.LIKES, FieldValue.arrayRemove(uid)).await()
                tsDoc1.get().addOnSuccessListener {
                    val likeCount = it.toObject(UserInfoDTO::class.java)!!.likes.size
                    result.invoke(
                        UiState.Success(
                            likeCount.toString()
                        )
                    )
                }.addOnFailureListener {
                    result.invoke(
                        UiState.Failure(
                            it.localizedMessage
                        )
                    )
                }.await()
            }
        }
    }
}