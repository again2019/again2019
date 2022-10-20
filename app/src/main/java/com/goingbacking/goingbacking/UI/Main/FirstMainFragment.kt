package com.goingbacking.goingbacking.UI.Main


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.goingbacking.goingbacking.*
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstMainFragment : BaseFragment<FragmentFirstMainBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFirstMainBinding {
        return FragmentFirstMainBinding.inflate(inflater, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tmpTimeButton.setOnClickListener {
            findNavController().navigate(R.id.action_firstMainFragment_to_tmpTimeActivity)
        }
    }



    }




