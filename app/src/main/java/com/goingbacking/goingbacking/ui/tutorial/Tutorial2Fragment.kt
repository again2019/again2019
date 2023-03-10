package com.goingbacking.goingbacking.ui.tutorial


import android.view.LayoutInflater
import android.view.ViewGroup
import com.goingbacking.goingbacking.ui.base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentTutorial2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Tutorial2Fragment : BaseFragment<FragmentTutorial2Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTutorial2Binding {
        return FragmentTutorial2Binding.inflate(layoutInflater, container, false)

    }
}