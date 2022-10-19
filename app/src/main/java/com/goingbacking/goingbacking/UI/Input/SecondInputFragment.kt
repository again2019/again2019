package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.FragmentSecondInputBinding
import com.goingbacking.goingbacking.databinding.FragmentSecondMainBinding
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

        SecondInputObserver()
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

    private fun SecondInputObserver() {
        viewModel.updateSecondInput.observe(viewLifecycleOwner) {
                state ->
            when(state) {
                is UiState.Failure -> {
                    Toast.makeText(requireActivity(), "fail", Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    Toast.makeText(requireActivity(), "success", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}