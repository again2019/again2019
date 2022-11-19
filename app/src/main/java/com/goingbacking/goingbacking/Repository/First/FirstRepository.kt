package com.goingbacking.goingbacking.Repository.First

import com.goingbacking.goingbacking.Model.TmpTimeDTO
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.FBConstants.Companion.DAY
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKMONTHINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.RANKYEARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.WHATTODOINFO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class FirstRepository(
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
) : FirstRepositoryIF {
    val myUid = user?.uid!!
    val cache = Source.CACHE

    /*
    TmpTimeActivity
     */

    // 임시 저장된 정보를 가져오는 코드
    override fun getTmpTimeInfo(result: (UiState<ArrayList<TmpTimeDTO>>) -> Unit) {
        firebaseFirestore.collection(FBConstants.TMPTIMEINFO).document(myUid)
            .collection(myUid).get()
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

    // 개인 정보(닉네임, 타입, 할 것)을 불러오는 부분
    override fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit) {
        firebaseFirestore.collection(Constants.USERINFO).document(myUid)
            .get(cache)
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

    /*
    WhatToDoSaveBottomSheet
     */

    // 임시 저장된 정보 -> 최종 정보로 바꾸고 삭제하는 코드
    override fun deleteTmpTimeInfo(startTime: String, result: (UiState<String>) -> Unit) {
        firebaseFirestore.collection(FBConstants.TMPTIMEINFO).document(myUid)
            .collection(myUid).document(myUid + startTime).delete()
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Day)
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
    }

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Month)
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

    // 임시 저장된 정보 -> 최종 정보로 바꾸는 코드 (Year)
    override fun updateTmpTimeYearInfo(
        wakeUpTime: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                    .collection("year").document(wakeUpTime)
                    .update("count", count)
    }

    // 임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Month)
    override fun updateRankMonthInfo(
        yyyyMM: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(RANKMONTHINFO).document(yyyyMM)
            .collection(yyyyMM).document(myUid)
            .update("count",count)
    }

    //임시 저장된 정보 -> 최종 랭크 정보로 바꾸는 코드 (Year)
    override fun updateRankYearInfo(
        yyyy: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(RANKYEARINFO).document(yyyy)
            .collection(yyyy).document(myUid)
            .update("count",count)
    }

    //임시 저장된 정보 -> 최종 정보로 어떤 자기계발을 할 것인지로 바꾸는 코드 (Month)
    override fun updateWhatToDoMonthInfo(
        yyyyMM :String,
        whatToDo: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection("month").document(yyyyMM)
            .collection(yyyyMM).document(myUid + whatToDo)
            .update("count", count)
    }

    //임시 저장된 정보 -> 최종 정보로 어떤 자기계발을 할 것인지로 바꾸는 코드 (Year)
    override fun updateWhatToDoYearInfo(
        yyyy :String,
        whatToDo: String,
        count: FieldValue,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(WHATTODOINFO).document(myUid)
            .collection("year").document(yyyy)
            .collection(yyyy).document(myUid + whatToDo)
            .update("count", count)
    }

    // 원하는 자기계발을 불러오느 코드
    override fun getWhatToDoInfo(result: (UiState<List<String>>) -> Unit) {
        firebaseFirestore.collection(FBConstants.USERINFO).document(myUid)
            .get()
            .addOnSuccessListener { document ->
                val data :UserInfoDTO? = document.toObject(UserInfoDTO::class.java)
                result.invoke(
                    UiState.Success(data?.whatToDoList!!)
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


}