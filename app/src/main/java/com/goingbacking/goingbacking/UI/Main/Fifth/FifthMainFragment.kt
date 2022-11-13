package com.goingbacking.goingbacking.UI.Main.Fifth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Login.LoginActivity
import com.goingbacking.goingbacking.databinding.FragmentFifthMainBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.makeInVisible
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FifthMainFragment : BaseFragment<FragmentFifthMainBinding>() {
    val viewModel: FifthViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFifthMainBinding {
        return FragmentFifthMainBinding.inflate(inflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    override fun onResume() {
        super.onResume()

        observer()
    }


    private fun observer() = with(binding) {
        viewModel.getFifthUserInfo()
        viewModel.userInfoDTO.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    progressCircular.hide()
                    myNickNameTextView.text = state.data.userNickName
                    myTypeTextView.text = state.data.userType
                    val whatToDoList = state.data.whatToDoList
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
//            intent.putExtra("whatToDo", whatToDo)
            startActivity(intent)
        }


        // 로그아웃이 아니라 계정 탈퇴
        out.setOnClickListener {
            viewModel.logout()
            viewModel.logout.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Success -> {
                        toast(requireContext(), getString(R.string.logout_success))
                        PrefUtil.setCurrentUid(null, requireContext())
                        //initPref()
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        finishAffinity(requireActivity())
                    }

                }
            }
        }
    }

    private fun initPref() {
        PrefUtil.setHistoryWhatToDo(mutableSetOf(), requireContext())
        PrefUtil.setSecondsRemaining(0L, requireContext())
        PrefUtil.setTodayTotalTime(0, requireContext())
        PrefUtil.setEndTime(0, requireContext())
        PrefUtil.setStartTime(0L, requireContext())
        PrefUtil.setTodayWhatToDo("",requireContext())
        PrefUtil.setTodayWhatToDoTime("", requireContext())
    }

}