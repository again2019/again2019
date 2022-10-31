package com.goingbacking.goingbacking.UI.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.Adapter.ForthFragmentPagerAdapter
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.FragmentForthMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForthMainFragment : BaseFragment<FragmentForthMainBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForthMainBinding {
        return FragmentForthMainBinding.inflate(inflater, container, false)

    }

    private val viewModel : ForthViewModel by viewModels()
    private val tabTitleArray = arrayOf (
        "이번 달",
        "이번 년도"
            )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.forthViewPager
        val tabs = binding.forthTabs
        viewPager.adapter = ForthFragmentPagerAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(tabs,viewPager) { tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

    }
}