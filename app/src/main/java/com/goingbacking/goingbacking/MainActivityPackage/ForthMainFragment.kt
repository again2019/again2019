package com.goingbacking.goingbacking.MainActivityPackage

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goingbacking.goingbacking.databinding.FragmentForthMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForthMainFragment : Fragment() {

    lateinit var binding:FragmentForthMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentForthMainBinding.inflate(layoutInflater)

        val builder : AlertDialog.Builder? = AlertDialog.Builder(requireActivity())
        builder?.setTitle("안내")?.setMessage("서비스 준비중입니다.")
        builder?.show()

        if(this::binding.isInitialized) {
            return binding.root
        } else {
            binding = FragmentForthMainBinding.inflate(layoutInflater)
            return binding.root
        }
    }
}