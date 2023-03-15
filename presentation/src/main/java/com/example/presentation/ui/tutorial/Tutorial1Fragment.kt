package com.example.presentation.ui.tutorial


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.databinding.FragmentTutorial1Binding
import com.example.presentation.ui.base.BaseFragment
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