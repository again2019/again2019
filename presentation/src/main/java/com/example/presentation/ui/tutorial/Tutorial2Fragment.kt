package com.example.presentation.ui.tutorial


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.presentation.databinding.FragmentTutorial2Binding
import com.example.presentation.ui.base.BaseFragment
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