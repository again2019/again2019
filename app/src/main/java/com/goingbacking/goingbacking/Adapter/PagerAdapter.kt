package com.goingbacking.goingbacking.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment1
import com.goingbacking.goingbacking.MainActivityPackage.FirstMainFragment2

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> FirstMainFragment1().newInstance()
            else -> FirstMainFragment2().newInstance()


        }

    }


}