package com.goingbacking.goingbacking.Repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class ForthRepository (
    val user : FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
        ) : ForthRepositoryIF {

            val myUid = user?.uid
















        }