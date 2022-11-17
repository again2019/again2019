package com.goingbacking.goingbacking.Repository.Forth

import com.goingbacking.goingbacking.FCM.FirebaseTokenManager
import com.goingbacking.goingbacking.FCM.NotificationData
import com.goingbacking.goingbacking.FCM.PushNotification
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RankRepository  (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
): RankRepositoryIF {
    val uid = user?.uid!!

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

    override fun getSecondSaveYearInfo(destinationUid : String, result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit) {

        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.YEAR).get()
            .addOnSuccessListener {
                val saveTimeYearDTOList = arrayListOf<SaveTimeYearDTO>()
                for(document in it){
                    saveTimeYearDTOList.add(document.toObject(SaveTimeYearDTO::class.java))
                }

                result.invoke(
                    UiState.Success(saveTimeYearDTOList)
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

    override fun getSecondWhatToDoYearInfo(destinationUid : String, result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)  {
        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection(FBConstants.YEAR).document(currentday("yyyy"))
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

    override fun getSaveTimeMonthInfo(
        destinationUid: String,
        result: (UiState<NewSaveTimeMonthDTO>) -> Unit
    ) {

        firebaseFirestore.collection(FBConstants.RANKMONTHINFO).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(destinationUid).get()
            .addOnSuccessListener { document ->
                val doc = document.toObject(NewSaveTimeMonthDTO::class.java)!!
                result.invoke(UiState.Success(doc))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(Constants.FAIL))
            }
    }

    override fun getSaveTimeYearInfo(
        destinationUid: String,
        result: (UiState<NewSaveTimeYearDTO>) -> Unit
    ) {
        firebaseFirestore.collection(FBConstants.RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(destinationUid).get()
            .addOnSuccessListener { document ->
                val doc = document.toObject(NewSaveTimeYearDTO::class.java)!!
                result.invoke(UiState.Success(doc))
            }.addOnFailureListener {
                result.invoke(UiState.Failure(Constants.FAIL))
            }
    }

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

    // 달별
    override fun likeButtonInfo(destinationUid :String, state :String, result: (UiState<String>) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {
            val tsDoc1 = firebaseFirestore.collection(Constants.USERINFO).document(destinationUid)
            if (state.equals("plus")) {
                tsDoc1.update(Constants.LIKES, FieldValue.arrayUnion(uid)).await()
                tsDoc1.get().addOnSuccessListener {
                    val likeCount = it.toObject(UserInfoDTO::class.java)!!.likes.size


                    PushNotification(
                        NotificationData(it.toObject(UserInfoDTO::class.java)!!.userNickName!! + "의 좋아요 수가 늘었어요!", it.toObject(UserInfoDTO::class.java)!!.userNickName!! + "의 좋아요 수가 늘었어요!"),
                        it.toObject(UserInfoDTO::class.java)!!.token!!
                    ).also {
                        FirebaseTokenManager.sendNotification(it)
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