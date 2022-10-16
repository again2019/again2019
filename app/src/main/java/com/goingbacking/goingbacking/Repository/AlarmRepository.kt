package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.FBConstants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.DAY
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
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

    val myUid = user?.uid!!

    // 현재 날짜에 대한 format
    var curNotifyTime = Calendar.getInstance()
    var currentDateTime = curNotifyTime.time

    var cur_date_text1 = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(currentDateTime).toString()
    var cur_date_text2 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime).toString()
    var cur_date_text3 = SimpleDateFormat("yyyy", Locale.getDefault()).format(currentDateTime).toString()
    var cur_date_text4 = SimpleDateFormat("MM", Locale.getDefault()).format(currentDateTime).toString()
    var cur_date_text5 = SimpleDateFormat("dd", Locale.getDefault()).format(currentDateTime).toString()

    // 맨 처음 로그인 시 month 초기화
    override fun addFirstInitSaveTimeMonthInfo(result: (UiState<String>) -> Unit) {

        var saveTimeMonthDTO = SaveTimeMonthDTO()
        saveTimeMonthDTO!!.month = cur_date_text4.toInt()
        saveTimeMonthDTO!!.year = cur_date_text3.toInt()
        saveTimeMonthDTO!!.count = 0

        firebaseFirestore?.collection(SAVETIMEINFO)?.document(myUid)
            ?.collection(MONTH)?.document(cur_date_text3)
            ?.collection(cur_date_text3)?.document(myUid + cur_date_text2)
            ?.set(saveTimeMonthDTO!!)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("addInitSaveTimeMonth Success")
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
        var saveTimeYearDTO = SaveTimeYearDTO()
        saveTimeYearDTO!!.year = cur_date_text3.toInt()
        saveTimeYearDTO!!.count = 0

        firebaseFirestore?.collection(SAVETIMEINFO)?.document(myUid)
            ?.collection(YEAR)?.document(cur_date_text3)
            ?.set(saveTimeYearDTO!!)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("addInitSaveTimeYear Success")
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

        var saveTimeDayDTO  = SaveTimeDayDTO()
        saveTimeDayDTO!!.day = cur_date_text5.toInt()
        saveTimeDayDTO!!.month = cur_date_text4.toInt()
        saveTimeDayDTO!!.year = cur_date_text3.toInt()
        saveTimeDayDTO!!.count = 0

        firebaseFirestore?.collection(SAVETIMEINFO)?.document(myUid)
            ?.collection(DAY)?.document(cur_date_text1)
            ?.collection(cur_date_text1)?.document(myUid + cur_date_text2)
            ?.set(saveTimeDayDTO!!)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("addInitSaveTimeDay Success")
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
        var beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        var beforeDateTime = beforeNotifyTime.time

        var bef_date_text1 = SimpleDateFormat("MM", Locale.getDefault()).format(beforeDateTime).toString()
        if (bef_date_text1 != cur_date_text4) {
            var saveTimeMonthDTO = SaveTimeMonthDTO()
            saveTimeMonthDTO!!.month = cur_date_text4.toInt()
            saveTimeMonthDTO!!.year = cur_date_text3.toInt()
            saveTimeMonthDTO!!.count = 0

            firebaseFirestore?.collection(SAVETIMEINFO)?.document(myUid)
                ?.collection(MONTH)?.document(cur_date_text3)
                ?.collection(cur_date_text3)?.document(myUid + cur_date_text2)
                ?.set(saveTimeMonthDTO!!)
                .addOnSuccessListener {
                    result.invoke(
                        UiState.Success("addInitSaveTimeMonth Success")
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
        var beforeNotifyTime = Calendar.getInstance()
        beforeNotifyTime.add(Calendar.DATE, -1)
        var beforeDateTime = beforeNotifyTime.time

        var bef_date_text1 = SimpleDateFormat("MM", Locale.getDefault()).format(beforeDateTime).toString()

        if (bef_date_text1 != cur_date_text3) {

            var saveTimeYearDTO = SaveTimeYearDTO()
            saveTimeYearDTO!!.year = cur_date_text3.toInt()
            saveTimeYearDTO!!.count = 0

            firebaseFirestore?.collection(SAVETIMEINFO)?.document(myUid)
                ?.collection(YEAR)?.document(cur_date_text3)
                ?.set(saveTimeYearDTO!!)
                .addOnSuccessListener {
                    result.invoke(
                        UiState.Success("addInitSaveTimeYear Success")
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

    override fun getTodayInfo(result: (UiState<ArrayList<CalendarInfoDTO>>) -> Unit) {


        var now = LocalDate.now()
        var Strnow1 = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        var Strnow2 = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        Log.d("experiment", "now: " + Strnow2)

        firebaseFirestore.collection(CALENDARINFO).document(myUid)
            ?.collection(Strnow1).whereEqualTo("date", Strnow2)?.get()
            .addOnSuccessListener {
                val TodayDTOList = arrayListOf<CalendarInfoDTO>()
                for (document in it) {
                    TodayDTOList.add(document.toObject(CalendarInfoDTO::class.java))
                }

                result.invoke(
                    UiState.Success(TodayDTOList)
                )
            }
            ?.addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

}