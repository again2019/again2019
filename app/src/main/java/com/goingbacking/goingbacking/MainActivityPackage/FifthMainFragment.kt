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
    lateinit var binding: FragmentFifthMainBinding
    val viewModel: MainViewModel by viewModels()


    // View를 생성하는 function
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFifthMainBinding.inflate(layoutInflater)
        binding.changeInfoTextView.setOnClickListener {
            moveChangeInfoActivity()
        }

        if(this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentFifthMainBinding.inflate(layoutInflater)
            return binding.root
        }
    }

    // 생성된 View를 처리하는 function
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

    }

    private fun observer() {
        viewModel.getFifthUserInfo()
        viewModel.userInfoDTO.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    binding.myNickNameTextView.text = state.data.userNickName
                    binding.myTypeTextView.text = state.data.userType
                    binding.myWhatToDoTextView.text = state.data.whatToDo
                }
                is UiState.Failure -> {
                    Log.e("experiment", state.error.toString())
                }
            }

        }
    }


    fun moveChangeInfoActivity() {
        val intent : Intent? = Intent(activity, ChangeInfoActivity::class.java)
        intent?.putExtra("userNickName", "")
        intent?.putExtra("userType", "userType")
        intent?.putExtra("whatToDo", "whatToDo")
        startActivity(intent)
    }


}