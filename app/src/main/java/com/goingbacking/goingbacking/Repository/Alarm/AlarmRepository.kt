package com.goingbacking.goingbacking.Repository.Alarm

import android.util.Log
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.*
import com.goingbacking.goingbacking.util.Constants.Companion.DATE
import com.goingbacking.goingbacking.util.Constants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.Constants.Companion.DATE2
import com.goingbacking.goingbacking.util.Constants.Companion.DAY
import com.goingbacking.goingbacking.util.Constants.Companion.MONTH
import com.goingbacking.goingbacking.util.Constants.Companion.RANKMONTHINFO
import com.goingbacking.goingbacking.util.Constants.Companion.RANKYEARINFO
import com.goingbacking.goingbacking.util.Constants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.Constants.Companion.TMPTIMEINFO
import com.goingbacking.goingbacking.util.Constants.Companion.USERINFO
import com.goingbacking.goingbacking.util.Constants.Companion.YEAR
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.collections.ArrayList


class AlarmRepository : AlarmRepositoryIF {
    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val myUid = FirebaseAuth.getInstance().currentUser?.uid!!
    private val cache = Source.CACHE
    // 맨 처음 로그인 시 month 초기화
    override fun addFirstInitSaveTimeMonthInfo() {

        val saveTimeMonthDTO = SaveTimeMonthDTO(
            month = currentday("MM").toInt(),
            year = currentday("yyyy").toInt(),
            count = 0
        )
        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid + currentday("dd"))
            .set(saveTimeMonthDTO)

    }

    // 맨 처음 로그인 시 year 초기화
    override fun addFirstInitSaveTimeYearInfo() {
        val saveTimeYearDTO = SaveTimeYearDTO(
            year = currentday("yyyy").toInt(),
            count = 0
        )

        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(YEAR).document(currentday("yyyy"))
            .set(saveTimeYearDTO)

    }


    // day마다 초기화
    override fun addInitSaveTimeDayInfo() {

        val saveTimeDayDTO  = SaveTimeDayDTO(
            day = currentday("dd").toInt() ,
            month = currentday("MM").toInt(),
            year = currentday("yyyy").toInt(),
            count = 0
        )


        firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
            .collection(DAY).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid + currentday("dd"))
            .set(saveTimeDayDTO)

    }

    // month마다 초기화
    override fun addInitSaveTimeMonthInfo() {
        if (beforeday("MM") != currentday("MM")) {
            val saveTimeMonthDTO = SaveTimeMonthDTO(
                month = currentday("MM").toInt(),
                year = currentday("yyyy").toInt(),
                count = 0
            )

            firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                .collection(MONTH).document(currentday("yyyy"))
                .collection(currentday("yyyy")).document(myUid + currentday("MM"))
                .set(saveTimeMonthDTO)

        }

    }

    // year마다 초기화
    override fun addInitSaveTimeYearInfo() {
        if (beforeday("yyyy") != currentday("yyyy")) {

            val saveTimeYearDTO = SaveTimeYearDTO(
                year = currentday("yyyy").toInt(),
                count = 0
            )

            firebaseFirestore.collection(SAVETIMEINFO).document(myUid)
                .collection(YEAR).document(currentday("yyyy"))
                .set(saveTimeYearDTO)


        }
    }

    override fun getTodayInfo(result: (ArrayList<Event>) -> Unit) {

        val TodayDTOList = arrayListOf<Event>()

        Log.d("experiemnt", currentday("yyyy-MM"))
        Log.d("experiemnt", currentday("yyyy-MM-dd"))

        firebaseFirestore.collection(CALENDARINFO).document(myUid)
            .collection(currentday("yyyy-MM")).whereEqualTo(DATE2, currentday("yyyy-MM-dd")).get()
            .addOnSuccessListener {

                for (document in it) {

                    TodayDTOList.add(document.toObject(Event::class.java))
                }

                Log.d("experiemnt", TodayDTOList.toString())

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
        currentTime: String,
        tmpTimeDTO: TmpTimeDTO
    ) {
        firebaseFirestore.collection(TMPTIMEINFO).document(myUid)
            .collection(myUid).document(myUid + currentTime).set(tmpTimeDTO)
    }


    override fun addInitWhatToDoMonthInfo(whatToDOList : MutableSet<String>) {
        if (beforeday("MM") != currentday("MM")) {
            for (whattodo in whatToDOList) {
                val whatToDoMonthDTO = WhatToDoMonthDTO()
                whatToDoMonthDTO.whatToDo = whattodo
                whatToDoMonthDTO.month = currentday("MM").toInt()
                whatToDoMonthDTO.count = 0

                firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
                    .collection(MONTH).document(currentday("yyyy-MM"))
                    .collection(currentday("yyyy-MM")).document(myUid + whattodo)
                    .set(whatToDoMonthDTO)
            }
        }
    }

    override fun addInitWhatToDoYearInfo(whatToDOList : MutableSet<String>) {
        if (beforeday("yyyy") != currentday("yyyy")) {
            for (whattodo in whatToDOList) {
                val whatToDoYearDTO = WhatToDoYearDTO()
                whatToDoYearDTO.whatToDo = whattodo
                whatToDoYearDTO.year = currentday("yyyy").toInt()
                whatToDoYearDTO.count = 0

                firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
                    .collection(YEAR).document(currentday("yyyy"))
                    .collection(currentday("yyyy")).document(myUid + whattodo)
                    .set(whatToDoYearDTO)

            }
        }

    }

    override fun addInitRankInfo() {
        CoroutineScope(Dispatchers.IO).launch {

            val userInfo = firebaseFirestore.collection(USERINFO).document(myUid).get(cache).await().toObject(UserInfoDTO::class.java)!!

            if (beforeday("MM") != currentday("MM")) {
                val newSaveTimeMonthDTO = NewSaveTimeMonthDTO(
                    uid = myUid,
                    nickname = userInfo.userNickName,
                    count = 0
                )

                firebaseFirestore.collection(RANKMONTHINFO).document(currentday("yyyy-MM"))
                    .collection(currentday("yyyy-MM")).document(myUid).set(newSaveTimeMonthDTO)
            }

            if (beforeday("yyyy") != currentday("yyyy")) {

                val newSaveTimeYearDTO = NewSaveTimeYearDTO(
                    uid = myUid,
                    count = 0,
                    nickname = userInfo.userNickName,
                )
                firebaseFirestore.collection(RANKYEARINFO).document(currentday("yyyy"))
                    .collection(currentday("yyyy")).document(myUid).set(newSaveTimeYearDTO)

            }
        }
    }
}