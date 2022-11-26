package com.goingbacking.goingbacking.UI.Main.Second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.Adapter.SecondFragmentPagerAdapter
import com.goingbacking.goingbacking.UI.Base.BaseFragment

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
    val viewModel: SecondViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SecondFragmentPagerAdapter(requireActivity())
        val fragmentList = listOf(SecondMainFragment1(), SecondMainFragment2())
        adapter.fragmentList = fragmentList

        binding.viewPager.adapter = adapter
        }

    }
