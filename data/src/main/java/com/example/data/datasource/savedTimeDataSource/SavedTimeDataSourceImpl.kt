package com.example.data.datasource.savedTimeDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.SavedTimeAboutRankEntity
import com.example.data.entity.SavedTimeDayEntity
import com.example.data.entity.SavedTimeMonthEntity
import com.example.data.entity.SavedTimeYearEntity
import com.example.domain.util.*
import com.example.domain.util.Constants.Companion.COUNT
import com.example.domain.util.Constants.Companion.DAY
import com.example.domain.util.Constants.Companion.MONTH
import com.example.domain.util.Constants.Companion.RANK_MONTH_INFO
import com.example.domain.util.Constants.Companion.RANK_YEAR_INFO
import com.example.domain.util.Constants.Companion.SAVE_TIME_INFO
import com.example.domain.util.Constants.Companion.SUCCESS
import com.example.domain.util.Constants.Companion.YEAR
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class SavedTimeDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseUser: FirebaseUser,
) : SavedTimeDataSource {

    val myUid = firebaseUser.uid
    val cache = Source.CACHE
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMyInitSavedTimeMonthEntity(
        savedTimeMonthEntity: SavedTimeMonthEntity,
        onResult : (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
            .collection(MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid + currentday("MM"))
            .set(savedTimeMonthEntity)
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }
            .addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMyInitSavedTimeYearEntity(
        savedTimeYearEntity: SavedTimeYearEntity,
        onResult : (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
            .collection(Constants.YEAR).document(currentday("yyyy"))
            .set(savedTimeYearEntity)
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }
            .addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun initSavedTimeAboutRankEntity(
        savedTimeAboutRankEntity: SavedTimeAboutRankEntity,
        onResult : (DatabaseResult<String>) -> Unit
    ) {
        if (beforeday("MM") != currentday("MM")) {
            firebaseFirestore.collection(RANK_MONTH_INFO).document(currentday("yyyy-MM"))
                .collection(currentday("yyyy-MM")).document(myUid)
                .set(savedTimeAboutRankEntity)
                .addOnSuccessListener {
                    onResult(DatabaseResult.Success(SUCCESS))
                }
                .addOnFailureListener {
                    onResult(DatabaseResult.Failure(it.message!!))
                }
        }

        if (beforeday("yyyy") != currentday("yyyy")) {
            firebaseFirestore.collection(RANK_MONTH_INFO).document(currentday("yyyy"))
                .collection(currentday("yyyy")).document(myUid)
                .set(savedTimeAboutRankEntity)
                .addOnSuccessListener {
                    onResult(DatabaseResult.Success(SUCCESS))
                }
                .addOnFailureListener {
                    onResult(DatabaseResult.Failure(it.message!!))
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeAboutMonthRankEntity(
        savedTimeAboutMonthRankEntity: SavedTimeAboutRankEntity,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(RANK_MONTH_INFO).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid)
            .set(savedTimeAboutMonthRankEntity)
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }
            .addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeAboutYearRankEntity(
        savedTimeAboutYearRankEntity: SavedTimeAboutRankEntity,
        onResult: (DatabaseResult<String>) -> Unit
    ) {
        firebaseFirestore.collection(RANK_YEAR_INFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid)
            .set(savedTimeAboutYearRankEntity)
            .addOnSuccessListener {
                onResult(DatabaseResult.Success(SUCCESS))
            }
            .addOnFailureListener {
                onResult(DatabaseResult.Failure(it.message!!))
            }
    }

    // savedTimeAboutRank

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSavedTimeAboutMonthRankEntityList(
        onResult: (DatabaseResult<List<SavedTimeAboutRankEntity>>) -> Unit
    ) {
        firebaseFirestore.collection(RANK_MONTH_INFO).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).orderBy(COUNT, Query.Direction.DESCENDING).get()
            .addOnSuccessListener { documents ->
                onResult(DatabaseResult.Success(documents.toObjects()))
            }
            .await().toObjects<SavedTimeAboutRankEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSavedTimeAboutYearRankEntityList(): ArrayList<SavedTimeAboutRankEntity> {
        return firebaseFirestore.collection(RANK_YEAR_INFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).orderBy(Constants.COUNT, Query.Direction.DESCENDING)
            .get().await().toObjects<SavedTimeAboutRankEntity>().toCollection(ArrayList())
    }

    override suspend fun updateSavedTimeAboutMonthRankEntity(yyyyMM: String, count: Double) {
        firebaseFirestore.collection(RANK_MONTH_INFO).document(yyyyMM)
            .collection(yyyyMM).document(myUid)
            .update("count", FieldValue.increment(count))
    }

    override suspend fun updateSavedTimeAboutYearRankEntity(yyyy: String, count: Double) {
        firebaseFirestore.collection(RANK_YEAR_INFO).document(yyyy)
            .collection(yyyy).document(myUid)
            .update("count", FieldValue.increment(count))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeDayEntity(savedTimeDayEntity: SavedTimeDayEntity) {
        firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
            .collection(Constants.DAY).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid + currentday("dd"))
            .set(savedTimeDayEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeMonthEntity(savedTimeMonthEntity: SavedTimeMonthEntity) {
        if (beforeday("MM") != currentday("MM")) {

            firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
                .collection(Constants.MONTH).document(currentday("yyyy"))
                .collection(currentday("yyyy")).document(myUid + currentday("MM"))
                .set(savedTimeMonthEntity)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeYearEntity(savedTimeYearEntity: SavedTimeYearEntity) {
        if (beforeday("yyyy") != currentday("yyyy")) {
            firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
                .collection(Constants.YEAR).document(currentday("yyyy"))
                .set(savedTimeYearEntity)
        }
    }

    // mySavedTime

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMySavedTimeDayEntity(): ArrayList<SavedTimeDayEntity> {
        return firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
            .collection(DAY).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache).await()
            .toObjects<SavedTimeDayEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMySavedTimeMonthEntity(): ArrayList<SavedTimeMonthEntity> {
        return firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
            .collection(MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache).await()
            .toObjects<SavedTimeMonthEntity>().toCollection(ArrayList())
    }

    // otherSavedTime

    override suspend fun getMySavedTimeYearEntity(): ArrayList<SavedTimeYearEntity> {
        return firebaseFirestore.collection(SAVE_TIME_INFO).document(myUid)
            .collection(YEAR).get(cache).await()
            .toObjects<SavedTimeYearEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOtherSavedTimeDayEntity(destinationUid: String): ArrayList<SavedTimeDayEntity> {
        return firebaseFirestore.collection(SAVE_TIME_INFO).document(destinationUid)
            .collection(DAY).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get().await()
            .toObjects<SavedTimeDayEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOtherSavedTimeMonthEntity(destinationUid: String): ArrayList<SavedTimeMonthEntity> {
        return firebaseFirestore.collection(SAVE_TIME_INFO).document(destinationUid)
            .collection(MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get().await()
            .toObjects<SavedTimeMonthEntity>().toCollection(ArrayList())
    }

    override suspend fun getOtherSavedTimeYearEntity(destinationUid: String): ArrayList<SavedTimeYearEntity> {
        return firebaseFirestore.collection(SAVE_TIME_INFO).document(destinationUid)
            .collection(YEAR).get().await()
            .toObjects<SavedTimeYearEntity>().toCollection(ArrayList())
    }
}