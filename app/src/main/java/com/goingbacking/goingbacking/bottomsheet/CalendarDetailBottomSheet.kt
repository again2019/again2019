package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ViewModel.MainViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetCalendarDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CalendarDetailBottomSheet : BottomSheetDialogFragment() {
  private lateinit var binding : BottomSheetCalendarDetailBinding
  private val viewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCalendarDetailBinding.inflate(inflater, container, false)




        return binding.root
    }


}