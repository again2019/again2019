package com.goingbacking.goingbacking.Repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RankRepository  (
    val user: FirebaseUser?,
    val firebaseFirestore: FirebaseFirestore
): RankRepositoryIF {
}