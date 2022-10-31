package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.FBConstants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.DAY
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.TMPTIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.YEAR
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class AlarmRepository (
    val user : FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
    ) : AlarmRepositoryIF {

    companion object {
        private const val success = "success"
        private const val fail = "fail"
        private const val DATE = "date"
    }
    private val myUid = user?.uid!!

    // 현재 날짜에 대한 format
    private var curNotifyTime = Calendar.getInstance()
    private var currentDateTime = curNotifyTime.time

    private var cur_date_text1 = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(currentDateTime).toString()
    private var cur_date_text2 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime).toString()
    private var cur_date_text3 = SimpleDateFormat("yyyy", Locale.getDefault()).format(currentDateTime).toString()
    private var cur_date_text4 = SimpleDateFormat("MM", Locale.getDefault()).format(currentDateTime).toString()
    private var cur_date_text5 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime).toString()

    // 맨 처음 로그인 시 month 초기화
    override fun addFirstInitSaveTimeMonthInfo(result: (UiState<String>) -> Unit) {

        val saveTimeMonthDTO = SaveTimeMonthDTO()
        saveTimeMonthDTO.month = cur_date_text4.toInt()
        saveTimeMonthDTO.year = cur_date_text3.toInt()
        saveTimeMonthDTO.count = 0

        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(MONTH).document(cur_date_text3)
            .collection(cur_date_text3).document(myUid + cur_date_text2)
            .set(saveTimeMonthDTO)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(success)
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

    // 맨 처음 로그인 시 year 초기화
    override fun addFirstInitSaveTimeYearInfo(result: (UiState<String>) -> Unit) {
        val saveTimeYearDTO = SaveTimeYearDTO()
        saveTimeYearDTO.year = cur_date_text3.toInt()
        saveTimeYearDTO.count = 0

        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(YEAR).document(cur_date_text3)
            .set(saveTimeYearDTO)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(success)
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


    // day마다 초기화
    override fun addInitSaveTimeDayInfo(
        result: (UiState<String>) -> Unit
    ) {

        val saveTimeDayDTO  = SaveTimeDayDTO()
        saveTimeDayDTO.day = cur_date_text5.toInt()
        saveTimeDayDTO.month = cur_date_text4.toInt()
        saveTimeDayDTO.year = cur_date_text3.toInt()
        saveTimeDayDTO.count = 0

        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(DAY).document(cur_date_text1)
            .collection(cur_date_text1).document(myUid + cur_date_text2)
            .set(saveTimeDayDTO)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success(success)
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

    // month마다 초기화
    override fun addInitSaveTimeMonthInfo(
        result: (UiState<String>) -> Unit
    ) {
        val beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        val beforeDateTime = beforeNotifyTime.time

        val bef_date_text1 = SimpleDateFormat("MM", Locale.getDefault()).format(beforeDateTime).toString()
        if (bef_date_text1 != cur_date_text4) {
            val saveTimeMonthDTO = SaveTimeMonthDTO()
            saveTimeMonthDTO.month = cur_date_text4.toInt()
            saveTimeMonthDTO.year = cur_date_text3.toInt()
            saveTimeMonthDTO.count = 0


            firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                .collection(MONTH).document(cur_date_text3)
                .collection(cur_date_text3).document(myUid + cur_date_text2)
                .set(saveTimeMonthDTO)
                .addOnSuccessListener {
                    result.invoke(
                        UiState.Success(success)
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

    // year마다 초기화
    override fun addInitSaveTimeYearInfo(
        result: (UiState<String>) -> Unit
    ) {
        val beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        val beforeDateTime = beforeNotifyTime.time

        val bef_date_text1 = SimpleDateFormat("yyyy", Locale.getDefault()).format(beforeDateTime).toString()

        if (bef_date_text1 != cur_date_text3) {

            val saveTimeYearDTO = SaveTimeYearDTO()
            saveTimeYearDTO.year = cur_date_text3.toInt()
            saveTimeYearDTO.count = 0

            firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                .collection(YEAR).document(cur_date_text3)
                .set(saveTimeYearDTO)
                .addOnSuccessListener {
                    result.invoke(
                        UiState.Success(success)
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

    override fun getTodayInfo(result: (ArrayList<CalendarInfoDTO>) -> Unit) {

        val TodayDTOList = arrayListOf<CalendarInfoDTO>()
        val now = LocalDate.now()
        val Strnow1 = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val Strnow2 = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        Log.d("experiment", "now: " + Strnow2)

        firebaseFirestore.collection(CALENDARINFO).document(myUid)
            .collection(Strnow1).whereEqualTo(DATE, Strnow2).get()
            .addOnSuccessListener {

                for (document in it) {
                    TodayDTOList.add(document.toObject(CalendarInfoDTO::class.java))
                }

                result.invoke(
                    TodayDTOList
                )
            }
            .addOnFailureListener {
                result.invoke(
                    TodayDTOList
                )
            }
    }

    override fun addTmpTimeInfo(
        tmpTimeDTO: TmpTimeDTO
    ) {
        firebaseFirestore.collection(TMPTIMEINFO).document(myUid)
            .collection(myUid).add(tmpTimeDTO)
    }


    override fun addInitWhatToDoMonthInfo(whatToDOList : MutableSet<String>) {
        val beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        val beforeDateTime = beforeNotifyTime.time

        val bef_date_text1 = SimpleDateFormat("MM", Locale.getDefault()).format(beforeDateTime).toString()
        if (bef_date_text1 != cur_date_text4) {
            for (whattodo in whatToDOList) {
                val whatToDoMonthDTO = WhatToDoMonthDTO()
                whatToDoMonthDTO.whatToDo = whattodo
                whatToDoMonthDTO.month = cur_date_text4.toInt()
                whatToDoMonthDTO.count = 0

                firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
                    .collection(MONTH).document(cur_date_text1)
                    .collection(cur_date_text1).document(myUid + whattodo)
                    .set(whatToDoMonthDTO)
            }
        }
    }

    override fun addInitWhatToDoYearInfo(whatToDOList : MutableSet<String>) {
        val beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        val beforeDateTime = beforeNotifyTime.time

        val bef_date_text1 = SimpleDateFormat("yyyy", Locale.getDefault()).format(beforeDateTime).toString()

        if (bef_date_text1 != cur_date_text3) {
            for (whattodo in whatToDOList) {
                val whatToDoYearDTO = WhatToDoYearDTO()
                whatToDoYearDTO.whatToDo = whattodo
                whatToDoYearDTO.year = cur_date_text3.toInt()
                whatToDoYearDTO.count = 0

                firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
                    .collection(YEAR).document(cur_date_text3)
                    .collection(cur_date_text3).document(myUid + whattodo)
                    .set(whatToDoYearDTO)

            }
        }

    }

}