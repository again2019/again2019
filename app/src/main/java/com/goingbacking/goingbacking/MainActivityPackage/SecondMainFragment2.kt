package com.goingbacking.goingbacking.MainActivityPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.FragmentSecondMain2Binding
import com.goingbacking.goingbacking.databinding.FragmentSecondMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondMainFragment2 : Fragment() {

    lateinit var binding : FragmentSecondMain2Binding
    val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondMain2Binding.inflate(layoutInflater)

        if (this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentSecondMain2Binding.inflate(layoutInflater)
            return binding.root
        }
    }
    fun newInstance() : SecondMainFragment2 {
        val args = Bundle()
        val frag = SecondMainFragment2()
        frag.arguments = args

        return frag
    }

}