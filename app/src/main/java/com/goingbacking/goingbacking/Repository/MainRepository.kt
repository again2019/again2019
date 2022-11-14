package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.FBConstants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.DATE
import com.goingbacking.goingbacking.util.FBConstants.Companion.DAY
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.WHATTODOINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.YEAR
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainRepository (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ): MainRepositoryIF {

    val uid = user?.uid!!
    val cache = Source.CACHE

    override fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit) {
        firebaseFirestore.collection(CALENDARINFO).document(uid)
            .collection(path1).document(path2)
            .set(event)
            .addOnSuccessListener {
                result.invoke(UiState.Success("ScheduleInput Success"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun addDateInfo(yearMonth : String, date: DateDTO, result: (UiState<String>) -> Unit) {
        firebaseFirestore.collection(DATE).document(uid)
            .collection(yearMonth).document(yearMonth).set(date)
            .addOnSuccessListener {
                result.invoke(UiState.Success("DateInfo Success"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }

    }

    override fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
                val dateDTO = firebaseFirestore.collection(DATE).document(uid)
                    .get().await().toObject(DateDTO::class.java)

                if (dateDTO == null) {
                    result.invoke(
                        UiState.Failure("fail")
                    )
                } else {
                    result.invoke(
                        UiState.Success(dateDTO)
                    )
                }

        }
    }


    override fun getThirdDateInfo2(year_month: String, result: (UiState<DateDTO>) -> Unit) {
            firebaseFirestore.collection(DATE).document(uid)
                .collection(year_month).document(year_month)
                .get()
                .addOnSuccessListener { document ->
                    val data: DateDTO? = document.toObject(DateDTO::class.java)
                    if (data == null) {
                        result.invoke(UiState.Failure("fail"))
                    } else {
                    result.invoke(UiState.Success(data))
                     }
                    }
                    .addOnFailureListener {
                        result.invoke(UiState.Failure(it.localizedMessage))
                    }
            }





    override fun getThirdCalendarInfo(yearList : MutableList<String>, result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit) {
        val events = mutableMapOf<LocalDate, List<Event>>()
        events.clear()
        for (i in yearList) {
            firebaseFirestore
                .collection(CALENDARINFO).document(uid).collection(currentday("yyyy-MM"))
                .whereEqualTo("date", i).get()
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

    override fun getSelectedDateInfo(year_month: String, date: String, result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit) {
        val events = mutableMapOf<LocalDate, List<Event>>()
        events.clear()

        firebaseFirestore.collection(CALENDARINFO).document(uid)
            .collection(year_month).whereEqualTo("date", date).get()
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




    // SecondMainFragment
    override fun getSecondSaveDayInfo(result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit) {
        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            .collection(DAY).document(currentday("yyyy-MM"))
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

    override fun getSecondSaveMonthInfo(result: (UiState<ArrayList<SaveTimeMonthDTO>>) -> Unit) {
        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            .collection(MONTH).document(currentday("yyyy"))
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

    override fun getSecondSaveYearInfo(result: (UiState<ArrayList<SaveTimeYearDTO>>) -> Unit) {

        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            .collection(YEAR).get()
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

    override fun getSecondWhatToDoMonthInfo(result: (UiState<ArrayList<WhatToDoMonthDTO>>) -> Unit) {
        firebaseFirestore.collection(WHATTODOINFO).document(uid)
            .collection(MONTH).document(currentday("yyyy-MM"))
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

    override fun getSecondWhatToDoYearInfo(result: (UiState<ArrayList<WhatToDoYearDTO>>) -> Unit)  {
        firebaseFirestore.collection(WHATTODOINFO).document(uid)
            .collection(YEAR).document(currentday("yyyy"))
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

}


