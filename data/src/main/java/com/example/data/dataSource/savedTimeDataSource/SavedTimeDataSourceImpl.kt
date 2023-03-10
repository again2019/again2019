package com.example.data.dataSource.savedTimeDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.SavedTimeDayEntity
import com.example.data.entity.SavedTimeMonthEntity
import com.example.data.entity.SavedTimeYearEntity
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
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
    override suspend fun getSavedTimeDayEntity(): ArrayList<SavedTimeDayEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection(FBConstants.DAY).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache).await()
            .toObjects<SavedTimeDayEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getSavedTimeMonthEntity(): ArrayList<SavedTimeMonthEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection(FBConstants.MONTH).document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache).await()
            .toObjects<SavedTimeMonthEntity>().toCollection(ArrayList())
    }

    override suspend fun getSavedTimeYearEntity(): ArrayList<SavedTimeYearEntity> {
        return firebaseFirestore.collection(FBConstants.SAVETIMEINFO).document(myUid)
            .collection("year").get(cache).await()
            .toObjects<SavedTimeYearEntity>().toCollection(ArrayList())
    }
}