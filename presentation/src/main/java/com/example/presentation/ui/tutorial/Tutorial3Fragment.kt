package com.example.presentation.ui.tutorial

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.databinding.FragmentTutorial3Binding
import com.example.presentation.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Tutorial3Fragment : BaseFragment<FragmentTutorial3Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTutorial3Binding {
        return FragmentTutorial3Binding.inflate(layoutInflater, container, false)
    }

}