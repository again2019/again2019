package com.goingbacking.goingbacking.MainActivityPackage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.android.synthetic.main.fragment_fifth_main.*
import kotlinx.android.synthetic.main.fragment_fifth_main.view.*


class FifthMainFragment : Fragment() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var userInfoDTO : UserInfoDTO? = null
    var userNickName: String? = null
    var userType: String? = null
    var whatToDo: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fifth_main, container, false)

        init()

        view.changeInfoTextView.setOnClickListener {
            moveChangeInfoActivity()
        }
        val source = Source.CACHE
        firebaseFirestore?.collection("UserInfo")?.document(userId!!)
            ?.get(source)?.addOnSuccessListener { documentSnapShot ->

                userNickName = documentSnapShot.getString("userNickName")
                userType = documentSnapShot.getString("userType")
                whatToDo = documentSnapShot.getString("whatToDo")
                myNickNameTextView.text = userNickName
                myTypeTextView.text = userType
                myWhatToDoTextView.text = whatToDo
                return@addOnSuccessListener
            }





        return view
    }

    fun init() {
        auth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        userId = auth?.currentUser?.uid
        userInfoDTO = UserInfoDTO()
    }

    fun moveChangeInfoActivity() {
        val intent : Intent? = Intent(activity, ChangeInfoActivity::class.java)
        intent?.putExtra("userNickName", userNickName)
        intent?.putExtra("userType", userType)
        intent?.putExtra("whatToDo", whatToDo)
        startActivity(intent)
    }


}