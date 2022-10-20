package com.goingbacking.goingbacking.UI.Main

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goingbacking.goingbacking.UI.Base.BaseFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val builder: AlertDialog.Builder? = AlertDialog.Builder(requireActivity())
        builder?.setTitle("안내")?.setMessage("서비스 준비중입니다.")
        builder?.show()

    }
}