package com.example.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.presentation.ui.main.forth.ForthMainFragment1
import com.example.presentation.ui.main.forth.ForthMainFragment2

private const val NUM_TABS = 2

class ForthFragmentPagerAdapter(fragmentManager: FragmentManager,lifecycle : Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return ForthMainFragment1()
            1 -> return ForthMainFragment2()
        }

        return ForthMainFragment1()

    }
}