package com.goingbacking.goingbacking.Repository

import android.util.Log
import android.widget.Toast
import com.goingbacking.goingbacking.Model.*
import com.goingbacking.goingbacking.util.FBConstants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.DATE
import com.goingbacking.goingbacking.util.FBConstants.Companion.DAY
import com.goingbacking.goingbacking.util.FBConstants.Companion.MONTH
import com.goingbacking.goingbacking.util.FBConstants.Companion.SAVETIMEINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.YEAR
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainRepository (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ): MainRepositoryIF {

    val uid = user?.uid!!
    val cache = Source.CACHE


    override fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit) {
        firebaseFirestore.collection(USERINFO)?.document(uid)
            ?.get(cache)
            ?.addOnSuccessListener { document ->
                val data :UserInfoDTO? = document.toObject(UserInfoDTO::class.java)
                result.invoke(
                    UiState.Success(data!!)
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

    override fun addEventInfo(path1: String, path2: String, event: Event, result: (UiState<String>) -> Unit) {
        firebaseFirestore?.collection(CALENDARINFO)?.document(uid)
            ?.collection(path1)?.document(path2)
            ?.set(event)
            .addOnSuccessListener {
                result.invoke(UiState.Success("ScheduleInput Success"))
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun addDateInfo(date: DateDTO, result: (UiState<String>) -> Unit) {
        firebaseFirestore?.collection(DATE)?.document(uid)?.set(date)
            ?.addOnSuccessListener {
                result.invoke(UiState.Success("DateInfo Success"))
            }
            ?.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }

    }

    override fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit) {
        firebaseFirestore?.collection(DATE)?.document(uid)
            ?.get(cache)
            ?.addOnSuccessListener { document ->
                val data: DateDTO? = document.toObject(DateDTO::class.java)
                result.invoke(
                    UiState.Success(data!!)
                )
            }
            ?.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun getThirdDateInfo2(result: (UiState<DateDTO>) -> Unit) {
        firebaseFirestore?.collection(DATE)?.document(uid)
            ?.get(cache)
            ?.addOnSuccessListener { document ->
                val data: DateDTO? = document.toObject(DateDTO::class.java)



                result.invoke(
                    UiState.Success(data!!)
                )
            }
            ?.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun getThirdCalendarInfo(yearList : MutableList<String>, result: (UiState<MutableMap<LocalDate, List<Event>>>) -> Unit) {
        val events = mutableMapOf<LocalDate, List<Event>>()
        var now = LocalDate.now()
        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM"))

        events.clear()
        for (i in yearList) {
            firebaseFirestore
                ?.collection(CALENDARINFO)?.document(uid)?.collection(Strnow)
                ?.whereEqualTo("date", i)?.get(cache)
                ?.addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.count() == 1) {
                        for (snapshot in querySnapshot!!) {
                            var x = LocalDate.parse(snapshot["date"].toString(), DateTimeFormatter.ISO_DATE)
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
                            var x = LocalDate.parse(snapshot["date"].toString(), DateTimeFormatter.ISO_DATE)

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
                ?.addOnFailureListener {
                    result.invoke(
                        UiState.Failure(it.localizedMessage)
                    )
                }

        }


    }


    // SecondMainFragment
    override fun getSecondSaveDayInfo(result: (UiState<ArrayList<SaveTimeDayDTO>>) -> Unit) {
        val current = LocalDateTime.now()
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy-MM")
        var curYearMonth = current.format(simpleDate1)



        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            ?.collection(DAY)?.document(curYearMonth)
            ?.collection(curYearMonth)?.get(cache)
            .addOnSuccessListener {
                var saveTimeDayDTOList = arrayListOf<SaveTimeDayDTO>()

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
        val current = LocalDateTime.now()
        val simpleDate1 = DateTimeFormatter.ofPattern("yyyy")

        var curYear = current.format(simpleDate1)

        firebaseFirestore.collection(SAVETIMEINFO).document(uid)
            ?.collection(MONTH)?.document(curYear)
            ?.collection(curYear).get(cache)
            .addOnSuccessListener {
                var saveTimeMonthDTOList = arrayListOf<SaveTimeMonthDTO>()

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
            ?.collection(YEAR)?.get(cache)
            .addOnSuccessListener {
                var saveTimeYearDTOList = arrayListOf<SaveTimeYearDTO>()
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


}