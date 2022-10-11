package com.goingbacking.goingbacking.Repository

import android.util.Log
import com.goingbacking.goingbacking.Model.CalendarInfoDTO
import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.Model.Event
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.util.FBConstants.Companion.CALENDARINFO
import com.goingbacking.goingbacking.util.FBConstants.Companion.DATE
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainRepository (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ): MainRepositoryIF {

    override fun getFifthUserInfo(result: (UiState<UserInfoDTO>) -> Unit) {
        firebaseFirestore.collection(USERINFO)?.document(user?.uid!!)
            ?.get(Source.CACHE)
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
        firebaseFirestore?.collection(CALENDARINFO)?.document(user?.uid!!)
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
        firebaseFirestore?.collection(DATE)?.document(user?.uid!!)?.set(date)
            ?.addOnSuccessListener {
                result.invoke(UiState.Success("DateInfo Success"))
            }
            ?.addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }

    }

    override fun getThirdDateInfo(result: (UiState<DateDTO>) -> Unit) {
        firebaseFirestore?.collection("Date")?.document(user?.uid!!)
            ?.get(Source.CACHE)
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
        firebaseFirestore?.collection("Date")?.document(user?.uid!!)
            ?.get(Source.CACHE)
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
                ?.collection("CalendarInfo")?.document(user?.uid!!)?.collection(Strnow)
                ?.whereEqualTo("date", i)?.get(Source.CACHE)
                ?.addOnSuccessListener { querySnapshot ->


                    if (querySnapshot == null) return@addOnSuccessListener

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


}