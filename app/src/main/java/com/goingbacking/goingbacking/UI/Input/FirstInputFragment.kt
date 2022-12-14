package com.goingbacking.goingbacking.UI.Input

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.Model.UserInfoDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.UI.Login.LoginActivity
import com.goingbacking.goingbacking.databinding.FragmentFirstInputBinding
import com.goingbacking.goingbacking.util.NetworkManager
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
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
        // ???????????? ???????????? ??????
        firstInputButton.setOnClickListener {
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                completeAction()
            }
        }
        // edittext?????? ???????????? '??????'??? ????????? ???????????? ???????????? ??????
        nickNameEdittext.setOnEditorActionListener { _, _, _ ->
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                completeAction()
            }
            true
        }
    }

    // ?????? ????????? ?????? ??????
    // ????????? ???????????? ?????? + ?????? fragment??? ?????????
    private fun completeAction() = with(binding) {
        // ????????? edittext??? ???????????????
        if (TextUtils.isEmpty(nickNameEdittext.text)) {
            toast(requireContext(), getString(R.string.nickname_input_fail))
        }
        // ????????? edittext??? ?????? ????????? ?????? ?????????
        else if (nickNameEdittext.text.length >= 10) {
            toast(requireContext(), getString(R.string.nickname_input_long_fail))
        }
        // ????????? edittext??? ???????????? ?????????
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