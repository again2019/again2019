package com.goingbacking.goingbacking.Repository.Third

import com.goingbacking.goingbacking.Model.DateDTO
import com.goingbacking.goingbacking.util.FBConstants
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source

class ThirdRepository(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth
) : ThirdRepositoryIF {
    val uid = firebaseAuth.currentUser?.uid!!
    val cache = Source.CACHE

    override fun addDateInfo(yearMonth : String, date: DateDTO, result: (UiState<String>) -> Unit) {
        firebaseFirestore.collection(FBConstants.DATE).document(uid)
            .collection(yearMonth).document(yearMonth).set(date)
    }

}