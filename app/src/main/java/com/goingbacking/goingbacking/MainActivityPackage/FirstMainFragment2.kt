package com.goingbacking.goingbacking.MainActivityPackage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goingbacking.goingbacking.R

class FirstMainFragment2 : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_main2, container, false)
    }

    fun newInstance():FirstMainFragment2{
        val args=Bundle()

        val frag=FirstMainFragment2()
        frag.arguments=args

        return frag
    }
}