package com.example.data.dataSource.userInfoDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.UserInfoEntity
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await

class UserInfoDataSourceImpl (
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseUser: FirebaseUser,
) : UserInfoDataSource {

    private val myUid = firebaseUser.uid
    private val cache = Source.CACHE


    override suspend fun getUserInfoEntity(): UserInfoEntity {
        return firebaseFirestore.collection(USERINFO).document(myUid)
            .get(cache).await().toObject(UserInfoEntity::class.java)!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateUserInfoEntity(
        nickname: String,
        type: String,
        selected: List<String>
    ) {
        val userInfoDocument =  firebaseFirestore.collection(Constants.USERINFO).document(myUid)
        userInfoDocument.update("userNickName", nickname)
        userInfoDocument.update("userType", type)
        userInfoDocument.update("whatToDoList", selected)

        firebaseFirestore.collection("RankMonthInfo").document(currentday("yyyy-MM"))
            .collection(currentday("yyyy-MM")).document(myUid)
            .update("nickname", nickname)

        firebaseFirestore.collection("RankYearInfo").document(currentday("yyyy"))
            .collection(currentday("yyyy")).document(myUid)
            .update("nickname", nickname)
    }
}