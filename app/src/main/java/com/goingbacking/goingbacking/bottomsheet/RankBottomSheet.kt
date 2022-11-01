package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.ViewModel.RankViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetRankBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RankBottomSheet : BottomSheetDialogFragment() {
  private lateinit var binding : BottomSheetRankBinding
  private val viewModel : RankViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetRankBinding.inflate(inflater, container, false)


        return binding.root
    }

}