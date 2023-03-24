package com.example.data.datasource.scheduleAndDateDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.DateEntity
import com.example.data.entity.ScheduleEntity
import com.example.domain.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class ScheduleAndDateDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseUser: FirebaseUser,
) : ScheduleAndDateDataSource {

    val myUid = firebaseUser.uid
    val cache = Source.CACHE

    override suspend fun addDateEntity(yearMonth: String, date: DateEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val tmp = firebaseFirestore.collection(FBConstants.DATE).document(myUid)
                .collection(yearMonth).document(yearMonth)
                .get().await().toObject(DateEntity::class.java)

            if (tmp == null) {
                firebaseFirestore.collection(FBConstants.DATE).document(myUid)
                    .collection(yearMonth).document(yearMonth)
                    .set(date)
            } else {
                for (subDate in date.dateList) {
                    firebaseFirestore.collection(FBConstants.DATE).document(myUid)
                        .collection(yearMonth).document(yearMonth)
                        .update("dateList", FieldValue.arrayUnion(subDate))
                }
            }
        }
    }

    override suspend fun addScheduleEntity(path1: String, path2: String, schedule: ScheduleEntity) {
        firebaseFirestore.collection(FBConstants.CALENDARINFO).document(myUid)
            .collection(path1).document(path2)
            .set(schedule)
    }

    override suspend fun getDateEntity(yearMonth: String): DateEntity {
        return firebaseFirestore.collection(FBConstants.DATE).document(myUid)
            .collection(yearMonth).document(yearMonth)
            .get().await().toObject(DateEntity::class.java) ?: DateEntity()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getDateEntityList(yearMonth: String): List<String> {
        var dateList = listOf<String>()
        for (i in 0..4) {
            val tmpYearMonth = YearMonth.now().plusMonths(i.toLong()).toString()
            val tmp = firebaseFirestore.collection(FBConstants.DATE).document(myUid)
                .collection(tmpYearMonth).document(tmpYearMonth)
                .get().await().toObject(DateEntity::class.java)
            if (tmp == null) {
                continue
            } else {
                dateList = dateList + tmp.dateList
            }
        }

        return dateList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteScheduleEntites(
        scheduleDate: String,
        timeStamp: String
    ): MutableMap<LocalDate, List<ScheduleEntity>> {

        firebaseFirestore.collection(FBConstants.DATE).document(myUid)
            .collection(currentday("yyyy-MM")).document(currentday("yyyy-MM"))
            .update("dateList", FieldValue.arrayRemove(scheduleDate)).await()

        firebaseFirestore.collection(FBConstants.CALENDARINFO).document(myUid)
            .collection(currentday("yyyy-MM")).document(timeStamp)
            .delete().await()

        val scheduleMap = mutableMapOf<LocalDate, List<ScheduleEntity>>()
        scheduleMap.clear()

        val scheduleList = firebaseFirestore.collection(FBConstants.CALENDARINFO).document(myUid)
            .collection(currentday("yyyy-MM")).whereEqualTo("date", scheduleDate).get()
            .await().toObjects<ScheduleEntity>().toCollection(ArrayList())

        when(scheduleList.count()) {
            1 -> {
                val date = LocalDate.parse(scheduleList[0].date, DateTimeFormatter.ISO_DATE)
                scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                    ScheduleEntity(
                        "move",
                        scheduleList[0].date,
                        scheduleList[0].start - scheduleList[0].start_t,
                        0,
                        scheduleList[0].start,
                        0
                    )
                )

                scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                    ScheduleEntity(
                        scheduleList[0].dest,
                        scheduleList[0].date,
                        scheduleList[0].start,
                        scheduleList[0].start_t,
                        scheduleList[0].end,
                        scheduleList[0].end_t,
                    )
                )

                scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                    ScheduleEntity(
                        "move",
                        scheduleList[0].date,
                        scheduleList[0].end,
                        0,
                        scheduleList[0].end + scheduleList[0].end_t,
                        0,
                    )
                )
            }

            else -> {
                var count = 1
                var before = ScheduleEntity("", "", 0,0,0, 0)
                scheduleList.forEach { schedule ->
                    val date = LocalDate.parse(schedule.date, DateTimeFormatter.ISO_DATE)

                    if (count == 1) {
                        scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                schedule.start - schedule.start_t,
                                0,
                                schedule.start,
                                0
                            )
                        )

                        scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                            ScheduleEntity(
                                scheduleList[0].dest,
                                scheduleList[0].date,
                                scheduleList[0].start,
                                scheduleList[0].start_t,
                                scheduleList[0].end,
                                scheduleList[0].end_t,
                            )
                        )
                    } else if (count == scheduleList.count()) {
                        scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                before.end,
                                0,
                                schedule.start,
                                0,
                            )
                        )

                        scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                            ScheduleEntity(
                                schedule.dest,
                                schedule.date,
                                schedule.start,
                                schedule.start_t,
                                schedule.end,
                                schedule.end_t,
                            )
                        )

                        scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                            ScheduleEntity(
                                "move",
                                    schedule.date,
                                    schedule.end,
                                0,
                                schedule.end + schedule.end_t,
                                0
                            )
                        )
                    } else {
                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    "move",
                                    schedule.date,
                                    before.end.toInt(),
                                    0,
                                    schedule.start,
                                    0,
                                )
                            )

                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    schedule.dest,
                                    schedule.date,
                                    schedule.start,
                                    schedule.start_t,
                                    schedule.end,
                                    schedule.end_t,
                                )
                            )
                        }

                    count += 1
                    before = ScheduleEntity(
                        schedule.dest,
                        schedule.date,
                        schedule.start,
                        schedule.start_t,
                        schedule.end,
                        schedule.end_t,
                    )
                }
            }
        }

        return scheduleMap
    }

    override suspend fun getSelectedScheduleEntities(
        yearMonth: String,
        date: String
    ): List<ScheduleEntity> {
        val scheduleMutableList = mutableListOf<ScheduleEntity>()

        val scheduleList = firebaseFirestore.collection(FBConstants.CALENDARINFO).document(myUid)
            .collection(yearMonth).whereEqualTo("date", date)
            .get().await().toObjects<ScheduleEntity>().toCollection(ArrayList())

        when (scheduleList.count()) {
            1 -> {
                scheduleMutableList.add(
                    ScheduleEntity(
                        "move",
                        scheduleList[0].date,
                        scheduleList[0].start - scheduleList[0].start_t,
                        0,
                        scheduleList[0].start,
                        0
                    )
                )
                scheduleMutableList.add(
                    ScheduleEntity(
                        scheduleList[0].dest,
                        scheduleList[0].date,
                        scheduleList[0].start,
                        scheduleList[0].start_t,
                        scheduleList[0].end,
                        scheduleList[0].end_t,
                    )
                )
                scheduleMutableList.add(
                    ScheduleEntity(
                        "move",
                        scheduleList[0].date,
                        scheduleList[0].end,
                        0,
                        scheduleList[0].end + scheduleList[0].end_t,
                        0
                    )
                )
            }
            else -> {
                var count = 1
                var before = ScheduleEntity("", "", 0, 0, 0, 0)
                scheduleList.forEach { schedule ->

                    if (count == 1) {
                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                schedule.start - schedule.start_t,
                                0,
                                schedule.start,
                                0
                            )
                        )

                        scheduleMutableList.add(
                            ScheduleEntity(
                                scheduleList[0].dest,
                                scheduleList[0].date,
                                scheduleList[0].start,
                                scheduleList[0].start_t,
                                scheduleList[0].end,
                                scheduleList[0].end_t,
                            )
                        )
                    } else if (count == scheduleList.count()) {
                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                before.end,
                                0,
                                schedule.start,
                                0,
                            )
                        )

                        scheduleMutableList.add(
                            ScheduleEntity(
                                schedule.dest,
                                schedule.date,
                                schedule.start,
                                schedule.start_t,
                                schedule.end,
                                schedule.end_t,
                            )
                        )

                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                schedule.end,
                                0,
                                schedule.end + schedule.end_t,
                                0
                            )
                        )
                    } else {
                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                before.end.toInt(),
                                0,
                                schedule.start,
                                0,
                            )
                        )

                        scheduleMutableList.add(
                            ScheduleEntity(
                                schedule.dest,
                                schedule.date,
                                schedule.start,
                                schedule.start_t,
                                schedule.end,
                                schedule.end_t,
                            )
                        )
                    }

                    count += 1
                    before = ScheduleEntity(
                        schedule.dest,
                        schedule.date,
                        schedule.start,
                        schedule.start_t,
                        schedule.end,
                        schedule.end_t,
                    )
                    if (count == 1) {
                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                schedule.start - schedule.start_t,
                                0,
                                schedule.start,
                                0,
                            )
                        )

                        scheduleMutableList.add(
                            ScheduleEntity(
                                schedule.dest,
                                schedule.date,
                                schedule.start,
                                schedule.start_t,
                                schedule.end,
                                schedule.end_t,
                            )
                        )

                    } else if (count == scheduleList.count()) {
                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                before.end.toInt(),
                                0,
                                schedule.start,
                                0,
                            )
                        )


                        scheduleMutableList.add(
                            ScheduleEntity(
                                schedule.dest,
                                schedule.date,
                                schedule.start,
                                schedule.start_t,
                                schedule.end,
                                schedule.end_t,
                            )
                        )

                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                schedule.end,
                                0,
                                schedule.end + schedule.end_t,
                                0,
                            )
                        )

                    } else {
                        scheduleMutableList.add(
                            ScheduleEntity(
                                "move",
                                schedule.date,
                                before.end,
                                0,
                                schedule.start,
                                0,
                            )
                        )

                        scheduleMutableList.add(
                            ScheduleEntity(
                                schedule.dest,
                                schedule.date,
                                schedule.start,
                                schedule.start_t,
                                schedule.end,
                                schedule.end_t,
                            )
                        )
                    }

                    count += 1
                    before = ScheduleEntity(
                        schedule.dest,
                        schedule.date,
                        schedule.start,
                        schedule.start_t,
                        schedule.end,
                        schedule.end_t,
                    )
                }
            }
        }
        return scheduleMutableList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllScheduleEntities(): MutableMap<LocalDate, List<ScheduleEntity>> {

        val scheduleMap = mutableMapOf<LocalDate, List<ScheduleEntity>>()
        scheduleMap.clear()

        val yearList = firebaseFirestore.collection("Date").document(myUid)
            .collection(currentday("yyyy-MM")).document(currentday("yyyy-MM")).get().await()
            .toObject(DateEntity::class.java)!!.dateList

        for (i in yearList) {

            val scheduleList = firebaseFirestore.collection(FBConstants.CALENDARINFO).document(myUid)
                .collection(currentday("yyyy-MM")).whereEqualTo("date", i).get()
                .await().toObjects<ScheduleEntity>().toCollection(ArrayList())


            when(scheduleList.count()) {
                1 -> {
                    val date = LocalDate.parse(scheduleList[0].date, DateTimeFormatter.ISO_DATE)
                    scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                        ScheduleEntity(
                            "move",
                            scheduleList[0].date,
                            scheduleList[0].start - scheduleList[0].start_t,
                            0,
                            scheduleList[0].start,
                            0
                        )
                    )

                    scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                        ScheduleEntity(
                            scheduleList[0].dest,
                            scheduleList[0].date,
                            scheduleList[0].start,
                            scheduleList[0].start_t,
                            scheduleList[0].end,
                            scheduleList[0].end_t,
                        )
                    )

                    scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                        ScheduleEntity(
                            "move",
                            scheduleList[0].date,
                            scheduleList[0].end,
                            0,
                            scheduleList[0].end + scheduleList[0].end_t,
                            0,
                        )
                    )
                }

                else -> {
                    var count = 1
                    var before = ScheduleEntity("", "", 0,0,0, 0)
                    scheduleList.forEach { schedule ->
                        val date = LocalDate.parse(schedule.date, DateTimeFormatter.ISO_DATE)

                        if (count == 1) {
                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    "move",
                                    schedule.date,
                                    schedule.start - schedule.start_t,
                                    0,
                                    schedule.start,
                                    0
                                )
                            )

                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    scheduleList[0].dest,
                                    scheduleList[0].date,
                                    scheduleList[0].start,
                                    scheduleList[0].start_t,
                                    scheduleList[0].end,
                                    scheduleList[0].end_t,
                                )
                            )
                        } else if (count == scheduleList.count()) {
                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    "move",
                                    schedule.date,
                                    before.end,
                                    0,
                                    schedule.start,
                                    0,
                                )
                            )

                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    schedule.dest,
                                    schedule.date,
                                    schedule.start,
                                    schedule.start_t,
                                    schedule.end,
                                    schedule.end_t,
                                )
                            )

                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    "move",
                                    schedule.date,
                                    schedule.end,
                                    0,
                                    schedule.end + schedule.end_t,
                                    0
                                )
                            )
                        } else {
                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    "move",
                                    schedule.date,
                                    before.end.toInt(),
                                    0,
                                    schedule.start,
                                    0,
                                )
                            )

                            scheduleMap[date] = scheduleMap[date].orEmpty().plus(
                                ScheduleEntity(
                                    schedule.dest,
                                    schedule.date,
                                    schedule.start,
                                    schedule.start_t,
                                    schedule.end,
                                    schedule.end_t,
                                )
                            )
                        }

                        count += 1
                        before = ScheduleEntity(
                            schedule.dest,
                            schedule.date,
                            schedule.start,
                            schedule.start_t,
                            schedule.end,
                            schedule.end_t,
                        )
                    }
                }

            }
        }

        return scheduleMap
    }


}
