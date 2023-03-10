package com.example.data.dataSource.whatToDoDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.WhatToDoMonthEntity
import com.example.data.entity.WhatToDoYearEntity
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

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
}