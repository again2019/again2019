package com.goingbacking.goingbacking.UI.Main


import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.goingbacking.goingbacking.*
import com.goingbacking.goingbacking.databinding.FragmentFirstMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FirstMainFragment : Fragment() {

    //임시 코드
    enum class TimerState {
        start, stop, pause
    }
    //임시 코드

    lateinit var binding :FragmentFirstMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstMainBinding.inflate(layoutInflater)
        binding.tmpTimeButton.setOnClickListener {
            moveTmpActivity()
        }

        if (this:: binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentFirstMainBinding.inflate(layoutInflater)
            return binding.root
        }


    }

    private fun moveTmpActivity() {
        val intent = Intent(requireContext(), TmpTimeActivity::class.java)
        startActivity(intent)
    }

}