package com.goingbacking.goingbacking.MainActivityPackage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.goingbacking.goingbacking.Adapter.PagerAdapter
import com.goingbacking.goingbacking.ViewModel.MainViewModel

import com.goingbacking.goingbacking.databinding.FragmentSecondMainBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment : Fragment() {

    lateinit var binding : FragmentSecondMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSecondMainBinding.inflate(layoutInflater)

        val paperAdapter = PagerAdapter(childFragmentManager)
        binding.viewPager.adapter = paperAdapter
        binding.indicator.setViewPager(binding.viewPager)



        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentSecondMainBinding.inflate(layoutInflater)
            return binding.root
        }

    }









}