package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetCheerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CheerBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetCheerBinding
    private val viewModel : ForthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCheerBinding.inflate(inflater, container, false)


        



        return binding.root

    }

}