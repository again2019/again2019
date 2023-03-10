package com.goingbacking.goingbacking.ui.input

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ui.base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentSecondInputBinding
import com.goingbacking.goingbacking.util.NetworkManager
import com.goingbacking.goingbacking.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondInputFragment : BaseFragment<FragmentSecondInputBinding>() {
    val viewModel: InputViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondInputBinding {
        return FragmentSecondInputBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args : SecondInputFragmentArgs by navArgs()
        // 오류 고치기
        binding.nickname.text = args.nickname
        binding.progressBar.setMinAndMaxProgress(0.05f, 0.355f)
        binding.progressBar.playAnimation()

        onClick()



        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backbtn.setOnClickListener {

            findNavController().navigate(R.id.action_secondInputFragment_to_firstInputFragment)
        }

    }


    private fun onClick() = with(binding) {

        loginbtn.setOnClickListener {
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                completeAction()
            }
        }
        typeEdittext.setOnEditorActionListener { _, _, _ ->
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
        if (TextUtils.isEmpty(typeEdittext.text)) {
            toast(requireContext(), "직업란이 비었어요.")
        }
        // 만약에 edittext의 입력 길이가 너무 길다면
        else if (typeEdittext.text.length >= 10) {
            toast(requireContext(), "직업명이 너무 길어요.")
        }
        // 만약에 edittext가 비어있지 않다면
        else {
            viewModel.updateSecondInput(typeEdittext.text.toString())

            val action = SecondInputFragmentDirections.actionSecondInputFragmentToThirdInputFragment(binding.nickname.text.toString())

            findNavController().navigate(action)
        }
    }



}