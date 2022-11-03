package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.*
import com.goingbacking.goingbacking.util.Constants.Companion.DATE
import com.goingbacking.goingbacking.util.Constants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.Constants.Companion.DAY
import com.goingbacking.goingbacking.util.Constants.Companion.MONTH
import com.goingbacking.goingbacking.util.Constants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.Constants.Companion.TMPTIMEINFO
import com.goingbacking.goingbacking.util.Constants.Companion.YEAR
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


    private val myUid = user?.uid!!

    // 맨 처음 로그인 시 month 초기화
    override fun addFirstInitSaveTimeMonthInfo(result: (UiState<String>) -> Unit) {

        val saveTimeMonthDTO = SaveTimeMonthDTO(
            month = mm().toInt(),
            year = yyyy().toInt(),
            count = 0
        )
        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(MONTH).document(yyyy())
            .collection(yyyy()).document(myUid + dd())
            .set(saveTimeMonthDTO)

    }

    // 맨 처음 로그인 시 year 초기화
    override fun addFirstInitSaveTimeYearInfo(result: (UiState<String>) -> Unit) {
        val saveTimeYearDTO = SaveTimeYearDTO(
            year = yyyy().toInt(),
            count = 0
        )

        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(YEAR).document(yyyy())
            .set(saveTimeYearDTO)

    }


    // day마다 초기화
    override fun addInitSaveTimeDayInfo(
        result: (UiState<String>) -> Unit
    ) {

        val saveTimeDayDTO  = SaveTimeDayDTO(
            day = dd().toInt() ,
            month = mm().toInt(),
            year = yyyy().toInt(),
            count = 0
        )


        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(DAY).document(yyyymm())
            .collection(yyyymm()).document(myUid + dd())
            .set(saveTimeDayDTO)

    }

    // month마다 초기화
    override fun addInitSaveTimeMonthInfo(
        result: (UiState<String>) -> Unit
    ) {
        val beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        val beforeDateTime = beforeNotifyTime.time

        val bef_date_text1 = SimpleDateFormat("MM", Locale.getDefault()).format(beforeDateTime).toString()
        if (bef_date_text1 != mm()) {
            val saveTimeMonthDTO = SaveTimeMonthDTO(
                month = mm().toInt(),
                year = yyyy().toInt(),
                count = 0
            )

            firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                .collection(MONTH).document(yyyy())
                .collection(yyyy()).document(myUid + dd())
                .set(saveTimeMonthDTO)

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

        if (bef_date_text1 != yyyy()) {

            val saveTimeYearDTO = SaveTimeYearDTO(
                year = yyyy().toInt(),
                count = 0
            )

            firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                .collection(YEAR).document(yyyy())
                .set(saveTimeYearDTO)


        }
    }

    override fun getTodayInfo(result: (ArrayList<CalendarInfoDTO>) -> Unit) {

        val TodayDTOList = arrayListOf<CalendarInfoDTO>()

        firebaseFirestore.collection(CALENDARINFO).document(myUid)
            .collection(yyyymm()).whereEqualTo(DATE, yyyymmdd(null)).get()
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
        if (bef_date_text1 != mm()) {
            for (whattodo in whatToDOList) {
                val whatToDoMonthDTO = WhatToDoMonthDTO()
                whatToDoMonthDTO.whatToDo = whattodo
                whatToDoMonthDTO.month = mm().toInt()
                whatToDoMonthDTO.count = 0

                firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
                    .collection(MONTH).document(yyyymm())
                    .collection(yyyymm()).document(myUid + whattodo)
                    .set(whatToDoMonthDTO)
            }
        }
    }

    override fun addInitWhatToDoYearInfo(whatToDOList : MutableSet<String>) {
        val beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        val beforeDateTime = beforeNotifyTime.time

        val bef_date_text1 = SimpleDateFormat("yyyy", Locale.getDefault()).format(beforeDateTime).toString()

        if (bef_date_text1 != yyyy()) {
            for (whattodo in whatToDOList) {
                val whatToDoYearDTO = WhatToDoYearDTO()
                whatToDoYearDTO.whatToDo = whattodo
                whatToDoYearDTO.year = yyyy().toInt()
                whatToDoYearDTO.count = 0

                firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
                    .collection(YEAR).document(yyyy())
                    .collection(yyyy()).document(myUid + whattodo)
                    .set(whatToDoYearDTO)

            }
        }

    }

}