package com.goingbacking.goingbacking.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.Adapter.DetailRankViewPagerAdapter
import com.goingbacking.goingbacking.UI.Main.Forth.RankFragment1
import com.goingbacking.goingbacking.UI.Main.Forth.RankFragment2
import com.goingbacking.goingbacking.ViewModel.RankViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetRankBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RankBottomSheet : BottomSheetDialogFragment() {
  private lateinit var binding : BottomSheetRankBinding
  private lateinit var detailRankViewPagerAdapter : DetailRankViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetRankBinding.inflate(inflater, container, false)

        initAdapter()

        return binding.root
    }


    private fun initAdapter () {
        val fragmentList = listOf(RankFragment1(), RankFragment2())
        detailRankViewPagerAdapter = DetailRankViewPagerAdapter(requireActivity())
        detailRankViewPagerAdapter.fragments.addAll(fragmentList)

        binding.rankViewPager2.adapter = detailRankViewPagerAdapter
    }
}