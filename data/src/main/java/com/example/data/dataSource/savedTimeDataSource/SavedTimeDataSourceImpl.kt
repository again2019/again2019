package com.example.data.dataSource.savedTimeDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.SavedTimeAboutRankEntity
import com.example.data.entity.SavedTimeDayEntity
import com.example.data.entity.SavedTimeMonthEntity
import com.example.data.entity.SavedTimeYearEntity
import com.goingbacking.goingbacking.AppConstants
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.beforeday
import com.goingbacking.goingbacking.util.currentday
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
    override suspend fun addMyInitSavedTimeMonthEntity(savedTimeMonthEntity: SavedTimeMonthEntity) {
        firebaseFirestore.collection(Constants.SAVETIMEINFO).document(myUid)
            .collection(Constants.MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid + currentday("MM"))
            .set(savedTimeMonthEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMyInitSavedTimeYearEntity(savedTimeYearEntity: SavedTimeYearEntity) {
        firebaseFirestore.collection(Constants.SAVETIMEINFO).document(myUid)
            .collection(Constants.YEAR).document(currentday("yyyy"))
            .set(savedTimeYearEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeAboutMonthRankEntity(savedTimeAboutMonthRankEntity: SavedTimeAboutRankEntity) {
        firebaseFirestore.collection(AppConstants.RANKMONTHINFO).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid)
            .set(savedTimeAboutMonthRankEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeAboutYearRankEntity(savedTimeAboutYearRankEntity: SavedTimeAboutRankEntity) {
        firebaseFirestore.collection(AppConstants.RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid)
            .set(savedTimeAboutYearRankEntity)
    }

    // savedTimeAboutRank

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSavedTimeAboutMonthRankEntity(): ArrayList<SavedTimeAboutRankEntity> {
        return firebaseFirestore.collection(FBConstants.RANKMONTHINFO).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).orderBy(Constants.COUNT, Query.Direction.DESCENDING).get()
            .await().toObjects<SavedTimeAboutRankEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSavedTimeAboutYearRankEntity(): ArrayList<SavedTimeAboutRankEntity> {
        return firebaseFirestore.collection(FBConstants.RANKYEARINFO).document(currentday("yyyy"))
            .collection(currentday("yyyy")).orderBy(Constants.COUNT, Query.Direction.DESCENDING)
            .get().await().toObjects<SavedTimeAboutRankEntity>().toCollection(ArrayList())
    }

    override suspend fun updateSavedTimeAboutMonthRankEntity(yyyyMM: String, count: Double) {
        firebaseFirestore.collection(FBConstants.RANKMONTHINFO).document(yyyyMM)
            .collection(yyyyMM).document(myUid)
            .update("count", FieldValue.increment(count))
    }

    override suspend fun updateSavedTimeAboutYearRankEntity(yyyy: String, count: Double) {
        firebaseFirestore.collection(FBConstants.RANKYEARINFO).document(yyyy)
            .collection(yyyy).document(myUid)
            .update("count", FieldValue.increment(count))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeDayEntity(savedTimeDayEntity: SavedTimeDayEntity) {
        firebaseFirestore.collection(Constants.SAVETIMEINFO).document(myUid)
            .collection(Constants.DAY).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid + currentday("dd"))
            .set(savedTimeDayEntity)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeMonthEntity(savedTimeMonthEntity: SavedTimeMonthEntity) {
        if (beforeday("MM") != currentday("MM")) {

            firebaseFirestore.collection(Constants.SAVETIMEINFO).document(myUid)
                .collection(Constants.MONTH).document(currentday("yyyy"))
                .collection(currentday("yyyy")).document(myUid + currentday("MM"))
                .set(savedTimeMonthEntity)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addMySavedTimeYearEntity(savedTimeYearEntity: SavedTimeYearEntity) {
        if (beforeday("yyyy") != currentday("yyyy")) {
            firebaseFirestore.collection(Constants.SAVETIMEINFO).document(myUid)
                .collection(Constants.YEAR).document(currentday("yyyy"))
                .set(savedTimeYearEntity)
        }
    }

    // mySavedTime

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMySavedTimeDayEntity(): ArrayList<SavedTimeDayEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection(FBConstants.DAY).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache).await()
            .toObjects<SavedTimeDayEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMySavedTimeMonthEntity(): ArrayList<SavedTimeMonthEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection(FBConstants.MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache).await()
            .toObjects<SavedTimeMonthEntity>().toCollection(ArrayList())
    }

    // otherSavedTime

    override suspend fun getMySavedTimeYearEntity(): ArrayList<SavedTimeYearEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection("year").get(cache).await()
            .toObjects<SavedTimeYearEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOtherSavedTimeDayEntity(destinationUid: String): ArrayList<SavedTimeDayEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.DAY).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get().await()
            .toObjects<SavedTimeDayEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOtherSavedTimeMonthEntity(destinationUid: String): ArrayList<SavedTimeMonthEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection(FBConstants.MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get().await()
            .toObjects<SavedTimeMonthEntity>().toCollection(ArrayList())
    }

    override suspend fun getOtherSavedTimeYearEntity(destinationUid: String): ArrayList<SavedTimeYearEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(destinationUid)
            .collection("year").get().await()
            .toObjects<SavedTimeYearEntity>().toCollection(ArrayList())
    }
}