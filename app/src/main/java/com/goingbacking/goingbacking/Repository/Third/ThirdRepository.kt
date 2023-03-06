package com.goingbacking.goingbacking.Repository.Third

import android.util.Log
import com.goingbacking.goingbacking.FCM.NotificationAPI
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.FBConstants.Companion.DATE
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ThirdRepository(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth
) : ThirdRepositoryIF {
    val uid = firebaseAuth.currentUser?.uid!!
    val cache = Source.CACHE


    /*
    ScheduleInputFragment2
     */

    // 날짜만 데이터 베이스에 저장
    override fun addDateInfo(yearMonth: String, date: DateDTO, result: (UiState<String>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val tmp = firebaseFirestore.collection(DATE).document(uid)
                .collection(yearMonth).document(yearMonth).get().await().toObject(DateDTO::class.java)

            if (tmp == null) {
                firebaseFirestore.collection(DATE).document(uid)
                    .collection(yearMonth).document(yearMonth).set(date)
            } else {
                for (subDate in date.dateList) {
                    firebaseFirestore.collection(DATE).document(uid)
                        .collection(yearMonth).document(yearMonth).update("dateList", FieldValue.arrayUnion(subDate))
                }
            }
        }


    }

    // 스케줄을 데이터 베이스에 저장
    override fun addEventInfo(
        path1: String,
        path2: String,
        event: Event,
        result: (UiState<String>) -> Unit
    ) {
        firebaseFirestore.collection(FBConstants.CALENDARINFO).document(uid)
            .collection(path1).document(path2)
            .set(event)
    }

    override fun getThirdDateInfo1(year_month: String, result: (UiState<DateDTO>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val tmp = firebaseFirestore.collection(DATE).document(uid)
                .collection(year_month).document(year_month)
                .get().await().toObject(DateDTO::class.java)


            Log.d("experiment", "tmp: " + tmp.toString())

            if (tmp == null) {
                result.invoke(UiState.Failure(
                    "fail"
                ))
            } else {
                result.invoke(UiState.Success(tmp))

            }

        }
    }

    /*
    ThirdMainFragment
     */

    // 날짜의 스케줄 유무를 가져오는 코드
    override fun getThirdDateInfo2(year_month: String, result: (UiState<List<String>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var dateList = listOf<String>()
            for (i in 0..4) {
                val tmp_year_month = YearMonth.now().plusMonths(i.toLong()).toString()
                val tmp = firebaseFirestore.collection(DATE).document(uid)
                    .collection(tmp_year_month).document(tmp_year_month)
                    .get().await().toObject(DateDTO::class.java)
                if (tmp == null) {
                    continue
                } else {
                    dateList = dateList + tmp.dateList
                }
            }

            if (dateList.size == 0) {
                result.invoke(UiState.Failure(
                    "fail"
                ))
            } else {
                result.invoke(UiState.Success(dateList))
            }

        }
    }

    // 날짜의 스케줄 삭제하는 코드
    override fun deleteThirdCalendarInfo(
        eventDate: String,
        timeStamp: String,
        result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            firebaseFirestore.collection(DATE).document(uid)
                .collection(currentday("yyyy-MM")).document(currentday("yyyy-MM"))
                .update("dateList", FieldValue.arrayRemove(eventDate)).await()

            firebaseFirestore.collection(FBConstants.CALENDARINFO).document(uid)
                .collection(currentday("yyyy-MM")).document(timeStamp).delete().await()

            val events = mutableMapOf<LocalDate, List<Event>>()
            events.clear()

            firebaseFirestore.collection(FBConstants.CALENDARINFO).document(uid)
                .collection(currentday("yyyy-MM")).whereEqualTo("date", eventDate).get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.count() == 1) {
                        for (snapshot in querySnapshot!!) {
                            val x = LocalDate.parse(snapshot["date"].toString(), DateTimeFormatter.ISO_DATE)
                            events[x] = events[x].orEmpty().plus(
                                Event(
                                    "move",
                                    snapshot["date"].toString(),
                                    snapshot["start"].toString().toInt() - snapshot["start_t"].toString().toInt(),
                                    0,
                                    snapshot["start"].toString().toInt(),
                                    0
                                )
                            )

                            events[x] = events[x].orEmpty().plus(
                                Event(
                                    snapshot["dest"].toString(),
                                    snapshot["date"].toString(),
                                    snapshot["start"].toString().toInt(),
                                    snapshot["start_t"].toString().toInt(),
                                    snapshot["end"].toString().toInt(),
                                    snapshot["end_t"].toString().toInt()
                                )
                            )

                            events[x] = events[x].orEmpty().plus(
                                Event(
                                    "move",
                                    snapshot["date"].toString(),
                                    snapshot["end"].toString().toInt(),
                                    0,
                                    snapshot["end"].toString().toInt() + snapshot["end_t"].toString().toInt(),
                                    0
                                )
                            )



                        }
                    }

                    else {
                        var count = 1
                        var before = Event("", "", 0,0,0)
                        for (snapshot in querySnapshot!!) {
                            val x = LocalDate.parse(snapshot["date"].toString(), DateTimeFormatter.ISO_DATE)

                            if (count == 1) {
                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        "move",
                                        snapshot["date"].toString(),
                                        snapshot["start"].toString().toInt() - snapshot["start_t"].toString().toInt(),
                                        0,
                                        snapshot["start"].toString().toInt(),
                                        0
                                    )
                                )

                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        snapshot["dest"].toString(),
                                        snapshot["date"].toString(),
                                        snapshot["start"].toString().toInt(),
                                        snapshot["start_t"].toString().toInt(),
                                        snapshot["end"].toString().toInt(),
                                        snapshot["end_t"].toString().toInt()
                                    )
                                )


                            } else if (count == querySnapshot.count()) {
                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        "move",
                                        snapshot["date"].toString(),
                                        before.end!!.toInt(),
                                        0,
                                        snapshot["start"].toString().toInt(),
                                        0,
                                    )
                                )


                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        snapshot["dest"].toString(),
                                        snapshot["date"].toString(),
                                        snapshot["start"].toString().toInt(),
                                        snapshot["start_t"].toString().toInt(),
                                        snapshot["end"].toString().toInt(),
                                        snapshot["end_t"].toString().toInt()
                                    )
                                )

                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        "move",
                                        snapshot["date"].toString(),
                                        snapshot["end"].toString().toInt(),
                                        0,
                                        snapshot["end"].toString().toInt() + snapshot["end_t"].toString().toInt(),
                                        0
                                    )
                                )

                            } else {

                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        "move",
                                        snapshot["date"].toString(),
                                        before.end!!.toInt(),
                                        0,
                                        snapshot["start"].toString().toInt(),
                                        0,
                                    )
                                )



                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        snapshot["dest"].toString(),
                                        snapshot["date"].toString(),
                                        snapshot["start"].toString().toInt(),
                                        snapshot["start_t"].toString().toInt(),
                                        snapshot["end"].toString().toInt(),
                                        snapshot["end_t"].toString().toInt()
                                    )
                                )


                            }

                            count = count + 1
                            before = Event(
                                snapshot["dest"].toString(),
                                snapshot["date"].toString(),
                                snapshot["start"].toString().toInt(),
                                snapshot["start_t"].toString().toInt(),
                                snapshot["end"].toString().toInt(),
                                snapshot["end_t"].toString().toInt()
                            )
                        }
                    }


                    result.invoke(
                        UiState.Success(events)
                    )
                }
                .addOnFailureListener {
                    result.invoke(
                        UiState.Failure(it.localizedMessage)
                    )
                }
        }
    }

    // 해당 달의 정보를 Map에 담는 코드
    override fun getAllCalendarInfo(
        result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val events = mutableMapOf<LocalDate, List<Event>>()
            events.clear()

            val yearList = firebaseFirestore.collection("Date").document(uid)
                .collection(currentday("yyyy-MM")).document(currentday("yyyy-MM")).get().await()
                .toObject(DateDTO::class.java)!!.dateList


            for (i in yearList) {
                firebaseFirestore
                    .collection(FBConstants.CALENDARINFO).document(uid)
                    .collection(currentday("yyyy-MM"))
                    .whereEqualTo("date", i).get()
                    .addOnSuccessListener { querySnapshot ->
                        if (querySnapshot.count() == 1) {
                            for (snapshot in querySnapshot!!) {
                                val x = LocalDate.parse(
                                    snapshot["date"].toString(),
                                    DateTimeFormatter.ISO_DATE
                                )
                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        "move",
                                        snapshot["date"].toString(),
                                        snapshot["start"].toString()
                                            .toInt() - snapshot["start_t"].toString().toInt(),
                                        0,
                                        snapshot["start"].toString().toInt(),
                                        0
                                    )
                                )

                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        snapshot["dest"].toString(),
                                        snapshot["date"].toString(),
                                        snapshot["start"].toString().toInt(),
                                        snapshot["start_t"].toString().toInt(),
                                        snapshot["end"].toString().toInt(),
                                        snapshot["end_t"].toString().toInt()
                                    )
                                )

                                events[x] = events[x].orEmpty().plus(
                                    Event(
                                        "move",
                                        snapshot["date"].toString(),
                                        snapshot["end"].toString().toInt(),
                                        0,
                                        snapshot["end"].toString()
                                            .toInt() + snapshot["end_t"].toString().toInt(),
                                        0
                                    )
                                )


                            }
                        } else {
                            var count = 1
                            var before = Event("", "", 0, 0, 0)
                            for (snapshot in querySnapshot!!) {
                                val x = LocalDate.parse(
                                    snapshot["date"].toString(),
                                    DateTimeFormatter.ISO_DATE
                                )

                                if (count == 1) {
                                    events[x] = events[x].orEmpty().plus(
                                        Event(
                                            "move",
                                            snapshot["date"].toString(),
                                            snapshot["start"].toString()
                                                .toInt() - snapshot["start_t"].toString().toInt(),
                                            0,
                                            snapshot["start"].toString().toInt(),
                                            0
                                        )
                                    )

                                    events[x] = events[x].orEmpty().plus(
                                        Event(
                                            snapshot["dest"].toString(),
                                            snapshot["date"].toString(),
                                            snapshot["start"].toString().toInt(),
                                            snapshot["start_t"].toString().toInt(),
                                            snapshot["end"].toString().toInt(),
                                            snapshot["end_t"].toString().toInt()
                                        )
                                    )


                                } else if (count == querySnapshot.count()) {
                                    events[x] = events[x].orEmpty().plus(
                                        Event(
                                            "move",
                                            snapshot["date"].toString(),
                                            before.end!!.toInt(),
                                            0,
                                            snapshot["start"].toString().toInt(),
                                            0,
                                        )
                                    )


                                    events[x] = events[x].orEmpty().plus(
                                        Event(
                                            snapshot["dest"].toString(),
                                            snapshot["date"].toString(),
                                            snapshot["start"].toString().toInt(),
                                            snapshot["start_t"].toString().toInt(),
                                            snapshot["end"].toString().toInt(),
                                            snapshot["end_t"].toString().toInt()
                                        )
                                    )

                                    events[x] = events[x].orEmpty().plus(
                                        Event(
                                            "move",
                                            snapshot["date"].toString(),
                                            snapshot["end"].toString().toInt(),
                                            0,
                                            snapshot["end"].toString()
                                                .toInt() + snapshot["end_t"].toString().toInt(),
                                            0
                                        )
                                    )

                                } else {

                                    events[x] = events[x].orEmpty().plus(
                                        Event(
                                            "move",
                                            snapshot["date"].toString(),
                                            before.end!!.toInt(),
                                            0,
                                            snapshot["start"].toString().toInt(),
                                            0,
                                        )
                                    )



                                    events[x] = events[x].orEmpty().plus(
                                        Event(
                                            snapshot["dest"].toString(),
                                            snapshot["date"].toString(),
                                            snapshot["start"].toString().toInt(),
                                            snapshot["start_t"].toString().toInt(),
                                            snapshot["end"].toString().toInt(),
                                            snapshot["end_t"].toString().toInt()
                                        )
                                    )


                                }

                                count = count + 1
                                before = Event(
                                    snapshot["dest"].toString(),
                                    snapshot["date"].toString(),
                                    snapshot["start"].toString().toInt(),
                                    snapshot["start_t"].toString().toInt(),
                                    snapshot["end"].toString().toInt(),
                                    snapshot["end_t"].toString().toInt()
                                )
                            }
                        }


                        result.invoke(
                            UiState.Success(events)
                        )
                    }
                    .addOnFailureListener {
                        result.invoke(
                            UiState.Failure(it.localizedMessage)
                        )
                    }

            }
        }

    }

    // 닉네임을 가져오는 코드
    override fun getNickNameInfo(result: (UiState<String>) -> Unit) {
        firebaseFirestore.collection(Constants.USERINFO).document(uid)
            .get(cache)
            .addOnSuccessListener { document ->
                val data: UserInfoDTO? = document.toObject(UserInfoDTO::class.java)
                result.invoke(
                    UiState.Success(data!!.userNickName!!)
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
    ThirdMainFragment
    TotalCalendarActivity
     */

    // 날짜의 선택된 스케줄을 가져오는 코드
    override fun getSelectedDateInfo(year_month: String, date: String, result: (UiState<MutableList<Event>>) -> Unit) {
        val events = mutableListOf<Event>()
        firebaseFirestore.collection(FBConstants.CALENDARINFO).document(uid)
            .collection(year_month).whereEqualTo("date", date).get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.count() == 1) {
                    for (snapshot in querySnapshot!!) {
                        events.add(
                            Event(
                                "move",
                                snapshot["date"].toString(),
                                snapshot["start"].toString()
                                    .toInt() - snapshot["start_t"].toString()
                                    .toInt(),
                                0,
                                snapshot["start"].toString().toInt(),
                                0
                            )
                        )
                        events.add(
                            Event(
                                snapshot["dest"].toString(),
                                snapshot["date"].toString(),
                                snapshot["start"].toString().toInt(),
                                snapshot["start_t"].toString().toInt(),
                                snapshot["end"].toString().toInt(),
                                snapshot["end_t"].toString().toInt()
                            )
                        )
                        events.add(
                            Event(
                                "move",
                                snapshot["date"].toString(),
                                snapshot["end"].toString().toInt(),
                                0,
                                snapshot["end"].toString().toInt() + snapshot["end_t"].toString()
                                    .toInt(),
                                0
                            )
                        )
                    }
                } else {
                    var count = 1
                    var before = Event("", "", 0, 0, 0)
                    for (snapshot in querySnapshot!!) {
                        if (count == 1) {
                            events.add(
                                Event(
                                    "move",
                                    snapshot["date"].toString(),
                                    snapshot["start"].toString()
                                        .toInt() - snapshot["start_t"].toString().toInt(),
                                    0,
                                    snapshot["start"].toString().toInt(),
                                    0
                                )
                            )

                            events.add(
                                Event(
                                    snapshot["dest"].toString(),
                                    snapshot["date"].toString(),
                                    snapshot["start"].toString().toInt(),
                                    snapshot["start_t"].toString().toInt(),
                                    snapshot["end"].toString().toInt(),
                                    snapshot["end_t"].toString().toInt()
                                )
                            )

                        } else if (count == querySnapshot.count()) {
                            events.add(
                                Event(
                                    "move",
                                    snapshot["date"].toString(),
                                    before.end!!.toInt(),
                                    0,
                                    snapshot["start"].toString().toInt(),
                                    0,
                                )
                            )


                            events.add(
                                Event(
                                    snapshot["dest"].toString(),
                                    snapshot["date"].toString(),
                                    snapshot["start"].toString().toInt(),
                                    snapshot["start_t"].toString().toInt(),
                                    snapshot["end"].toString().toInt(),
                                    snapshot["end_t"].toString().toInt()
                                )
                            )

                            events.add(
                                Event(
                                    "move",
                                    snapshot["date"].toString(),
                                    snapshot["end"].toString().toInt(),
                                    0,
                                    snapshot["end"].toString()
                                        .toInt() + snapshot["end_t"].toString().toInt(),
                                    0
                                )
                            )

                        } else {

                            events.add(
                                Event(
                                    "move",
                                    snapshot["date"].toString(),
                                    before.end!!.toInt(),
                                    0,
                                    snapshot["start"].toString().toInt(),
                                    0,
                                )
                            )



                            events.add(
                                Event(
                                    snapshot["dest"].toString(),
                                    snapshot["date"].toString(),
                                    snapshot["start"].toString().toInt(),
                                    snapshot["start_t"].toString().toInt(),
                                    snapshot["end"].toString().toInt(),
                                    snapshot["end_t"].toString().toInt()
                                )
                            )


                        }

                        count = count + 1
                        before = Event(
                            snapshot["dest"].toString(),
                            snapshot["date"].toString(),
                            snapshot["start"].toString().toInt(),
                            snapshot["start_t"].toString().toInt(),
                            snapshot["end"].toString().toInt(),
                            snapshot["end_t"].toString().toInt()
                        )
                    }
                }
                result.invoke(
                    UiState.Success(events)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(it.localizedMessage)
                )
            }

        }



}