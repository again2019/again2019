package com.example.data.dataSource.userInfoDataSource

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.data.entity.UserInfoEntity
import com.goingbacking.goingbacking.util.Constants
import com.goingbacking.goingbacking.util.FBConstants.Companion.USERINFO
import com.goingbacking.goingbacking.util.currentday
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserInfoDataSourceImpl (
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseUser: FirebaseUser,
    private val firebaseMessaging: FirebaseMessaging,
) : UserInfoDataSource {

    private val myUid = firebaseUser.uid
    private val cache = Source.CACHE

    override suspend fun addUserInfoEntity(userNickName: String) {
        val userInfoDTO = UserInfoEntity(
            uid = myUid,
            userNickName = userNickName,
            token = firebaseMessaging.token.await()
        )

        firebaseFirestore.collection(Constants.USERINFO).document(myUid)
            .set(userInfoDTO).await()

    }

    override suspend fun updateUserType(userType: String) {
        firebaseFirestore.collection(Constants.USERINFO).document(myUid)
            .update(Constants.USERTYPE, userType)
    }

    override suspend fun updateUserSelectedList(selected: List<String>) {
        firebaseFirestore.collection(Constants.USERINFO).document(myUid)
            .update(Constants.WHATTODOLIST, selected)
    }


    override suspend fun getMyUserInfoEntity(): UserInfoEntity {
        return firebaseFirestore.collection(USERINFO).document(myUid)
            .get(cache).await().toObject(UserInfoEntity::class.java)!!
    }

    override suspend fun getOtherUserInfoEntity(destinationUid: String): UserInfoEntity {
        return firebaseFirestore.collection(Constants.USERINFO).document(destinationUid)
            .get().await().toObject(UserInfoEntity::class.java)!!
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

    override suspend fun updateCheerList(destinationUid: String, cheer: String): List<String> {
        val document = firebaseFirestore.collection(Constants.USERINFO).document(destinationUid)
        document.update(Constants.CHEERS, FieldValue.arrayUnion(cheer)).await()
        return document.get().await().toObject(UserInfoEntity::class.java)!!.cheers
    }

    override suspend fun deleteCheer(destinationUid: String, cheer: String) {
        firebaseFirestore.collection(Constants.USERINFO).document(destinationUid)
            .update(Constants.CHEERS, FieldValue.arrayRemove(cheer))
    }

    override suspend fun updateLikeButton(destinationUid: String, state: String) : String {
        val destinationDoc = firebaseFirestore.collection(Constants.USERINFO).document(destinationUid)
        when (state) {
            "plus" -> {
                destinationDoc.update(Constants.LIKES, FieldValue.arrayUnion(myUid)).await()
                // notification
            }
            "minus" -> {
                destinationDoc.update(Constants.LIKES, FieldValue.arrayRemove(myUid)).await()
            }
        }

        return destinationDoc.get().await().toObject(UserInfoEntity::class.java)!!.likes.size.toString()

    }




}