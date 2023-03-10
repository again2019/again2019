package com.example.data.dataSource.tmpTimeDataSource

import com.example.data.entity.TmpTimeEntity
import com.goingbacking.goingbacking.util.FBConstants
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class TmpTimeDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseUser: FirebaseUser,
) : TmpTimeDataSource {

    val myUid = firebaseUser.uid
    val cache = Source.CACHE

    override suspend fun getTmpTimeEntity(): ArrayList<TmpTimeEntity> {
        return firebaseFirestore.collection("TmpTimeInfo").document(myUid)
            .collection(myUid).get().await().toObjects<TmpTimeEntity>().toCollection(ArrayList())
    }

    override suspend fun deleteTmpTimeEntity(startTime: String) {
        firebaseFirestore.collection(FBConstants.TMPTIMEINFO).document(myUid)
            .collection(myUid).document(myUid + startTime).delete()
    }

    override suspend fun updateTmpTimeDayEntity(
        wakeUpTime1: String,
        wakeupTime2: String,
        count: Double
    ) {
        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection(FBConstants.DAY).document(wakeUpTime1)
            .collection(wakeUpTime1).document(myUid + wakeupTime2)
            .update("count", FieldValue.increment(count))
    }

    override suspend fun updateTmpTimeMonthEntity(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double
    ) {
        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection(FBConstants.MONTH).document(wakeUpTime1)
            .collection(wakeUpTime1).document(myUid + wakeUpTime2)
            .update("count", count)
    }

    override suspend fun updateTmpTimeYearEntity(wakeUpTime: String, count: Double) {
        firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection("year").document(wakeUpTime)
            .update("count", count)
    }


}