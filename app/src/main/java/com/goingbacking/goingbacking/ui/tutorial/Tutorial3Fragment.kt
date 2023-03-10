package com.goingbacking.goingbacking.ui.tutorial

import android.view.LayoutInflater
import android.view.ViewGroup
import com.goingbacking.goingbacking.ui.base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentTutorial3Binding
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