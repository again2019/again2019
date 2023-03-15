package com.example.data.dataSource.whatToDoDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.WhatToDoMonthEntity
import com.example.data.entity.WhatToDoYearEntity
import com.example.domain.util.Constants
import com.example.domain.util.FBConstants
import com.example.domain.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class WhatToDoDataSourceImpl (
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseUser: FirebaseUser,
) : WhatToDoDataSource {

    private val myUid = firebaseUser.uid
    private val cache = Source.CACHE

    // WhatToDoMonth

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addWhatToDoMonthEntity(
        whatToDoMonthEntity: WhatToDoMonthEntity
    ) {
        firebaseFirestore.collection(Constants.WHATTODOINFO).document(myUid)
            .collection(Constants.MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid + whatToDoMonthEntity.whatToDo)
            .set(whatToDoMonthEntity)
    }

    override suspend fun updateWhatToDoMonthEntity(
        yyyyMM: String,
        whatToDo: String,
        count: Double
    ) {
        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
            .collection("month").document(yyyyMM)
            .collection(yyyyMM).document(myUid + whatToDo)
            .update("count", FieldValue.increment(count))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMyWhatToDoMonthEntity(): ArrayList<WhatToDoMonthEntity> {
        return firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
            .collection(FBConstants.MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).get(cache).await().toObjects<WhatToDoMonthEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOtherWhatToDoMonthEntity(destinationUid: String): ArrayList<WhatToDoMonthEntity> {
        return firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection(FBConstants.MONTH).document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).get().await().toObjects<WhatToDoMonthEntity>().toCollection(ArrayList())
    }

    // WhatToDoYear

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addWhatToDoYearEntity(
        whatToDoYearEntity: WhatToDoYearEntity
    ) {
        firebaseFirestore.collection(Constants.WHATTODOINFO).document(myUid)
            .collection(Constants.YEAR).document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid + whatToDoYearEntity.whatToDo)
            .set(whatToDoYearEntity)
    }

    override suspend fun updateWhatToDoYearEntity(yyyy: String, whatToDo: String, count: Double) {
        firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
            .collection("year").document(yyyy)
            .collection(yyyy).document(myUid + whatToDo)
            .update("count", FieldValue.increment(count))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMyWhatToDoYearEntity(): ArrayList<WhatToDoYearEntity> {
        return firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(myUid)
            .collection("year").document(currentday("yyyy"))
            .collection(currentday("yyyy")).get(cache).await().toObjects<WhatToDoYearEntity>().toCollection(ArrayList())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getOtherWhatToDoYearEntity(destinationUid: String): ArrayList<WhatToDoYearEntity> {
        return firebaseFirestore.collection(FBConstants.WHATTODOINFO).document(destinationUid)
            .collection("year").document(currentday("yyyy"))
            .collection(currentday("yyyy")).get().await().toObjects<WhatToDoYearEntity>().toCollection(ArrayList())
    }
}