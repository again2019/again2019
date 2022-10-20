package com.goingbacking.goingbacking.UI.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.Adapter.PagerAdapter
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.MainViewModel

import com.goingbacking.goingbacking.databinding.FragmentSecondMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment : BaseFragment<FragmentSecondMainBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondMainBinding {
        return FragmentSecondMainBinding.inflate(inflater, container, false)

    }
    val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paperAdapter = PagerAdapter(childFragmentManager)
        binding.viewPager.adapter = paperAdapter
        binding.indicator.setViewPager(binding.viewPager)
    }

    }
