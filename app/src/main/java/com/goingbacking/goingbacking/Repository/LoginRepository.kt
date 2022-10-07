package com.goingbacking.goingbacking.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    
//    fun getUser(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful) {
//                _userLiveData.postValue(firebaseAuth.currentUser)
//            } else {
//                //실패 처리
//            }
//        }
//    }

    fun getGoogleUser(idToken: String) :FirebaseUser?{
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        var firebaseUser : FirebaseUser? = null
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseUser = firebaseAuth.currentUser
            } else {
                //실패 처리
                firebaseUser = null
            }


        }

        return firebaseUser
    }
    
}