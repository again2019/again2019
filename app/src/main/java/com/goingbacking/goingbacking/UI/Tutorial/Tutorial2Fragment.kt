package com.goingbacking.goingbacking.UI.Tutorial


import android.view.LayoutInflater
import android.view.ViewGroup
import com.goingbacking.goingbacking.UI.Base.BaseFragment
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