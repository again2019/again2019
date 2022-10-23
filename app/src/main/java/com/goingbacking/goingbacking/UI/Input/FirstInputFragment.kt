package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.FragmentFirstInputBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstInputFragment : BaseFragment<FragmentFirstInputBinding>() {
    val viewModel: InputViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstInputBinding {
       return FragmentFirstInputBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    private fun onClick() = with(binding) {
        firstInputButton.setOnClickListener {
            FirstInputObserver()
            completeAction()
        }
        nickNameEdittext.setOnEditorActionListener { v, actionId, event ->
            completeAction()
            true
        }
    }

    // 입력 조건이 맞는 경우
    // 데이터 베이스에 저장 + 다음 fragment로 넘어감
    private fun completeAction() = with(binding) {
        // 만약에 edittext가 비어있다면
        if (TextUtils.isEmpty(nickNameEdittext.text)) {
            Toast.makeText(requireActivity(), "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show()
        }
        else if (nickNameEdittext.text.length >= 10) {
            Toast.makeText(requireActivity(), "닉네임이 너무 길어요.", Toast.LENGTH_SHORT).show()
        }
        // 만약에 edittext가 비어있지 않다면
        else {
            viewModel.addFirstInput(
                UserInfoDTO(
                    nickNameEdittext.text.toString(),
                    null,
                    null,
                    null,
                )
            )
            findNavController().navigate(R.id.action_firstInputFragment_to_secondInputFragment)
        }
    }


    private fun FirstInputObserver() {

        // **** 실패 시 report 하는 기능 ****
        viewModel.addFirstInput.observe(viewLifecycleOwner) {
                state ->
            when(state) {
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), "fail", Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Toast.makeText(requireActivity(), "success", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // -------------------------------
    }




}