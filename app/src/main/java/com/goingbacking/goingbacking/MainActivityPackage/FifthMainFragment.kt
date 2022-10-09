package com.goingbacking.goingbacking.MainActivityPackage

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.PrefUtil
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentFifthMainBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fifth_main.*
import kotlinx.android.synthetic.main.fragment_fifth_main.view.*


@AndroidEntryPoint
class FifthMainFragment : Fragment() {
    var auth : FirebaseAuth? = null
    var firebaseFirestore : FirebaseFirestore? = null
    var userId : String? = null
    var userInfoDTO : UserInfoDTO? = null
    var userNickName: String? = null
    var userType: String? = null
    var whatToDo: String? = null


    lateinit var binding: FragmentFifthMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFifthMainBinding.inflate(layoutInflater)

        init()


        binding.changeInfoTextView.setOnClickListener {
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





        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFifthUserInfo()
        viewModel.userInfoDTO.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    Log.e("experiment", "success")
                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }

        }
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