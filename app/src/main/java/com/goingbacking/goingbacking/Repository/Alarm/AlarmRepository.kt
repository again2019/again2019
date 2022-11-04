package com.goingbacking.goingbacking.Repository.Alarm

import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.*
import com.goingbacking.goingbacking.util.Constants.Companion.DATE
import com.goingbacking.goingbacking.util.Constants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.Constants.Companion.DAY
import com.goingbacking.goingbacking.util.Constants.Companion.MONTH
import com.goingbacking.goingbacking.util.Constants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.Constants.Companion.TMPTIMEINFO
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.Constants.Companion.YEAR
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AlarmRepository : AlarmRepositoryIF {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val myUid = FirebaseAuth.getInstance().currentUser?.uid!!
    private val cache = Source.CACHE
    // 맨 처음 로그인 시 month 초기화
    override fun addFirstInitSaveTimeMonthInfo() {

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
    override fun addFirstInitSaveTimeYearInfo() {
        val saveTimeYearDTO = SaveTimeYearDTO(
            year = yyyy().toInt(),
            count = 0
        )

        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(YEAR).document(yyyy())
            .set(saveTimeYearDTO)

    }


    // day마다 초기화
    override fun addInitSaveTimeDayInfo() {

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
    override fun addInitSaveTimeMonthInfo() {
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
    override fun addInitSaveTimeYearInfo() {
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

    override fun addInitRankInfo() {
        CoroutineScope(Dispatchers.IO).launch {

            val userInfo = firebaseFirestore.collection(USERINFO).document(myUid).get(cache).await().toObject(UserInfoDTO::class.java)!!

            val beforeNotifyTime = Calendar.getInstance()
            beforeNotifyTime.add(Calendar.DATE, -10)
            val beforeDateTime = beforeNotifyTime.time

            val bef_date_text1 = SimpleDateFormat("MM", Locale.getDefault()).format(beforeDateTime).toString()
            if (bef_date_text1 != mm()) {
                val newSaveTimeMonthDTO = NewSaveTimeMonthDTO(
                    uid = myUid,
                    token = userInfo.token,
                    nickname = userInfo.userNickName,
                    type = userInfo.userType,
                    whattodo = userInfo.whatToDoList,
                    count = 0
                )

                firebaseFirestore.collection("RankMonthInfo").document(yyyymm())
                    .collection(yyyymm()).document(myUid).set(newSaveTimeMonthDTO)
            }

            val bef_date_text2 = SimpleDateFormat("yyyy", Locale.getDefault()).format(beforeDateTime).toString()
            if (bef_date_text2 != yyyy()) {

                val newSaveTimeYearDTO = NewSaveTimeYearDTO(
                    uid = myUid,
                    count = 0,
                    nickname = userInfo.userNickName,
                    type = userInfo.userType,
                    whattodo = userInfo.whatToDoList,
                    token = userInfo.token,
                )
                firebaseFirestore.collection("RankYearInfo").document(yyyy())
                    .collection(yyyy()).document(myUid).set(newSaveTimeYearDTO)

            }
        }
    }



}