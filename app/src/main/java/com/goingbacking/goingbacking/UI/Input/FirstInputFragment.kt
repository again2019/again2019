package com.goingbacking.goingbacking.UI.Input

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentFirstInputBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
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

        binding.progressBar.setMinAndMaxProgress(0f, 0f)
        binding.progressBar.playAnimation()
        onClick()
    }


    private fun onClick() = with(binding) {
        // 다음으로 이동하는 버튼
        firstInputButton.setOnClickListener {
            completeAction()
        }
        // edittext에서 작성하고 '완료'를 누르면 다음으로 넘어가는 코드
        nickNameEdittext.setOnEditorActionListener { _, _, _ ->
            completeAction()
            true
        }
    }

    // 입력 조건이 맞는 경우
    // 데이터 베이스에 저장 + 다음 fragment로 넘어감
    private fun completeAction() = with(binding) {
        // 만약에 edittext가 비어있다면
        if (TextUtils.isEmpty(nickNameEdittext.text)) {
            toast(requireContext(), getString(R.string.nickname_input_fail))
        }
        // 만약에 edittext의 입력 길이가 너무 길다면
        else if (nickNameEdittext.text.length >= 10) {
            toast(requireContext(), getString(R.string.nickname_input_long_fail))
        }
        // 만약에 edittext가 비어있지 않다면
        else {
            viewModel.addFirstInput(nickNameEdittext.text.toString())
            findNavController().navigate(R.id.action_firstInputFragment_to_secondInputFragment)
        }
    }


}