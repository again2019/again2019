package com.example.data.dataSource

import android.util.Log
import com.example.data.entity.TmpTimeEntity
import com.example.domain.model.TmpTimeModel
import com.google.firebase.auth.FirebaseAuth
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
    val myUid = FirebaseAuth.getInstance().currentUser!!.uid
    val cache = Source.CACHE

    override suspend fun getTmpTimDTO(): ArrayList<TmpTimeEntity> {

        Log.d("experiemtnt", myUid)
        Log.d("experiemtnt", firebaseFirestore.collection("TmpTimeInfo").document(myUid)
            .collection(myUid).get().await().toObjects<TmpTimeModel>().toCollection(ArrayList()).toString())

        return firebaseFirestore.collection("TmpTimeInfo").document(myUid)
            .collection(myUid).get().await().toObjects<TmpTimeEntity>().toCollection(ArrayList())
    }
}