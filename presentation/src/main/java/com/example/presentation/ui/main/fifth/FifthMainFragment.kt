package com.example.presentation.ui.main.fifth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import com.example.domain.util.UiState
import com.example.domain.util.makeInVisible
import com.example.domain.util.makeVisible
import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.PrefUtil
import com.example.presentation.R
import com.example.presentation.databinding.FragmentFifthMainBinding
import com.example.presentation.ui.base.BaseFragment
import com.example.presentation.ui.login.LoginActivity

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FifthMainFragment : BaseFragment<FragmentFifthMainBinding>() {
    val viewModel: FifthViewModel by viewModels()
    private lateinit var whatToDoList : List<String>
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFifthMainBinding {
        return FragmentFifthMainBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()

        observer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        onClick()
    }

    private fun observer() = with(binding) {
        viewModel.getFifthUserInfo()
        viewModel.userInfoDTO.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    progressCircular.hide()
                    myNickNameTextView.text = state.data.userNickName
                    myTypeTextView.text = state.data.userType
                    whatToDoList = state.data.whatToDoList
                    if (whatToDoList.size == 0) {
                        chip1.makeInVisible()
                        chip2.makeInVisible()
                        chip3.makeInVisible()
                    } else if (whatToDoList.size == 1) {
                        chip1.text = whatToDoList.get(0)
                        chip2.makeInVisible()
                        chip3.makeInVisible()
                    } else if (whatToDoList.size == 2) {
                        chip1.text = whatToDoList.get(0)
                        chip2.text = whatToDoList.get(1)
                        chip1.makeVisible()
                        chip2.makeVisible()
                        chip3.makeInVisible()
                    } else {
                        chip1.text = whatToDoList.get(0)
                        chip2.text = whatToDoList.get(1)
                        chip3.text = whatToDoList.get(2)
                    }

                }
                is UiState.Failure -> {
                    progressCircular.hide()
                    toast(requireContext(), getString(R.string.get_user_info_fail))
                }
                is UiState.Loading -> {
                    progressCircular.show()

                }
            }

        }
    }

    private fun onClick() = with(binding) {
        changeInfoTextView.setOnClickListener {
            val nickName = myNickNameTextView.text.toString()
            val userType = myTypeTextView.text.toString()

            val intent = Intent(requireContext(), ChangeInfoActivity::class.java)
            intent.putExtra("nickName", nickName)
            intent.putExtra("userType", userType)
            intent.putExtra("whatToDo", whatToDoList.toTypedArray())
            startActivity(intent)
        }

        question.setOnClickListener {
            val intent = Intent(requireContext(), QuestionActivity::class.java)
            startActivity(intent)

        }

        // 로그아웃이 아니라 계정 탈퇴
        out.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage("계정을 탈퇴하겠습니까?")
                .setPositiveButton("탈퇴하기") { _, _ ->
                    if (!NetworkManager.checkNetworkState(requireContext())) {
                        toast(requireContext(), getString(R.string.network_fail))
                    } else {
                        viewModel.signout()
                        initPref()
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity(requireActivity())
                    }
                }
                .setNegativeButton("나가기", null)
                .show()
        }
    }

    private fun initPref() {
        PrefUtil.setRecentDate("", requireContext())
        PrefUtil.setHistoryWhatToDo(mutableSetOf(), requireContext())
        PrefUtil.setCurrentUid("", requireContext())
        PrefUtil.setSecondsRemaining(0L, requireContext())
        PrefUtil.setTodayTotalTime(0, requireContext())
        PrefUtil.setEndTime(0, requireContext())
        PrefUtil.setStartTime(0L, requireContext())
        PrefUtil.setTodayWhatToDo("",requireContext())
        PrefUtil.setTodayWhatToDoTime("", requireContext())
    }

}