package com.example.data.dataSource.tmpTimeDataSource

import com.example.data.entity.TmpTimeEntity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class TmpTimeDataSourceImpl(
    firebaseFirestore: FirebaseFirestore,
    firebaseUser: FirebaseUser,
) : TmpTimeDataSource {

    val firebaseFirestore = firebaseFirestore
    val myUid = firebaseUser.uid
    val cache = Source.CACHE


    override suspend fun getTmpTimDTO(): ArrayList<TmpTimeEntity> {
        return firebaseFirestore.collection("TmpTimeInfo").document(myUid)
            .collection(myUid).get().await().toObjects<TmpTimeEntity>().toCollection(ArrayList())
    }
}