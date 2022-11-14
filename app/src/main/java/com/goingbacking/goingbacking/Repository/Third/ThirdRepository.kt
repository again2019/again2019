package com.goingbacking.goingbacking.Repository.Third

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ThirdRepository(
    val firebaseFirestore: FirebaseFirestore,
    val firebaseAuth: FirebaseAuth
) : ThirdRepositoryIF {



}