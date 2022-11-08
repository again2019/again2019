package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.databinding.FragmentSecondInputBinding
import com.goingbacking.goingbacking.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondInputFragment : BaseFragment<FragmentSecondInputBinding>() {
    val viewModel: InputViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSecondInputBinding {
        return FragmentSecondInputBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.setMinAndMaxProgress(0f, 0.35f)
        binding.progressBar.playAnimation()
        onClick()

        setArrayAdapter()
        onClick()
    }

    private fun setArrayAdapter() {
        val arrayAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.type,
            android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.SecondInputSpinner.setAdapter(arrayAdapter)

    }

    private fun onClick() = with(binding) {
        SecondInputButton1.setOnClickListener {
            findNavController().navigate(R.id.action_secondInputFragment_to_firstInputFragment)
        }
        SecondInputButton2.setOnClickListener {
            viewModel.updateSecondInput(SecondInputSpinner.selectedItem.toString())

            findNavController().navigate(R.id.action_secondInputFragment_to_thirdInputFragment)
        }
    }


}