package com.goingbacking.goingbacking.UI.Main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.ForthViewModel
import com.goingbacking.goingbacking.databinding.FragmentForthMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForthMainFragment : BaseFragment<FragmentForthMainBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForthMainBinding {
        return FragmentForthMainBinding.inflate(inflater, container, false)

    }

    val viewModel : ForthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }
}