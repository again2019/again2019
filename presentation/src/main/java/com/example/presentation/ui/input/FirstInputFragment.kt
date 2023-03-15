package com.example.presentation.ui.input

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.presentation.ui.base.BaseFragment
import com.example.presentation.ui.login.LoginActivity
import com.example.domain.util.toast
import com.example.presentation.NetworkManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentFirstInputBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstInputFragment : BaseFragment<FragmentFirstInputBinding>() {
    val viewModel: InputViewModel by viewModels()
    private lateinit var callback: OnBackPressedCallback
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstInputBinding {
       return FragmentFirstInputBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.setMinAndMaxProgress(0f, 0.05f)
        binding.progressBar.playAnimation()


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        onClick()
    }

    private fun moveLoginPage() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }

    private fun onClick() = with(binding) {
        // 다음으로 이동하는 버튼
        firstInputButton.setOnClickListener {
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                completeAction()
            }
        }
        // edittext에서 작성하고 '완료'를 누르면 다음으로 넘어가는 코드
        nickNameEdittext.setOnEditorActionListener { _, _, _ ->
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                completeAction()
            }
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
            val action = FirstInputFragmentDirections.actionFirstInputFragmentToSecondInputFragment(nickNameEdittext.text.toString())

            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveLoginPage()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }


}