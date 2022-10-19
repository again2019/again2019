package com.goingbacking.goingbacking.UI.Tutorial


import android.view.LayoutInflater
import android.view.ViewGroup
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentTutorial1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Tutorial1Fragment : BaseFragment<FragmentTutorial1Binding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTutorial1Binding {
        return FragmentTutorial1Binding.inflate(layoutInflater, container, false)

    }
}