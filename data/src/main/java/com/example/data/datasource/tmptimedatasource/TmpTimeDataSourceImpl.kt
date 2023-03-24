package com.example.data.datasource.tmptimedatasource

import com.example.data.entity.TmpTimeEntity
import com.example.domain.util.Constants.Companion.COUNT
import com.example.domain.util.DatabaseResult
import com.example.domain.util.Constants.Companion.DAY
import com.example.domain.util.Constants.Companion.MONTH
import com.example.domain.util.Constants.Companion.SUCCESS
import com.example.domain.util.Constants.Companion.TMP_TIME_INFO
import com.example.domain.util.Constants.Companion.YEAR
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObjects

class TmpTimeDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseUser: FirebaseUser,
) : TmpTimeDataSource {

    val myUid = firebaseUser.uid
    val cache = Source.CACHE

    /*
        DoingReceiver
    */

    override suspend fun addTmpTimeEntity(
        currentTime: String,
        tmpTimeEntity: TmpTimeEntity,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        if(tmpTimeEntity.nowSeconds.toInt() != 0) {
            firebaseFirestore.collection(TMP_TIME_INFO).document(myUid)
                .collection(myUid).document(myUid + currentTime)
                .set(tmpTimeEntity)
                .addOnSuccessListener {
                    onResult(DatabaseResult.Success(SUCCESS))
                }
                .addOnFailureListener {
                    onResult(DatabaseResult.Failure(it.message!!))
                }
        }
    }

    /*
        TmpTimeActivity
    */

    override suspend fun deleteTmpTimeEntity(
        startTime: String,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(TMP_TIME_INFO).document(myUid)
            .collection(myUid).document(myUid + startTime)
            .delete()
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }
            .addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }

    /*
        FirstMainFragment
    */

    override suspend fun getTmpTimeEntityList(
        onResult: (DatabaseResult<List<TmpTimeEntity>>) -> Unit,
    ) {
        firebaseFirestore.collection(TMP_TIME_INFO).document(myUid)
            .collection(myUid).get()
            .addOnSuccessListener { documents ->
                onResult(DatabaseResult.Success(documents.toObjects()))
            }
            .addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }


    override suspend fun updateTmpTimeDayEntity(
        wakeUpTime1: String,
        wakeupTime2: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(TMP_TIME_INFO).document(myUid)
            .collection(DAY).document(wakeUpTime1)
            .collection(wakeUpTime1).document(myUid + wakeupTime2)
            .update(COUNT, FieldValue.increment(count))
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }
            .addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }

    override suspend fun updateTmpTimeMonthEntity(
        wakeUpTime1: String,
        wakeUpTime2: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(TMP_TIME_INFO).document(myUid)
            .collection(MONTH).document(wakeUpTime1)
            .collection(wakeUpTime1).document(myUid + wakeUpTime2)
            .update(COUNT, count)
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }.addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }

    override suspend fun updateTmpTimeYearEntity(
        wakeUpTime: String,
        count: Double,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(TMP_TIME_INFO).document(myUid)
            .collection(YEAR).document(wakeUpTime)
            .update(COUNT, count)
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }.addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }
}