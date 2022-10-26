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
import com.goingbacking.goingbacking.ViewModel.InputViewModel
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
            SecondInputObserver()

            findNavController().navigate(R.id.action_secondInputFragment_to_thirdInputFragment)
        }
    }


    // 데이터 베이스에 입력하는 것이 잘되었는지 확인하는 함수
    private fun SecondInputObserver() {
        viewModel.updateSecondInput.observe(viewLifecycleOwner) {
                state ->
            when(state) {
                is UiState.Failure -> {
                }
                is UiState.Success -> {
                }
                is UiState.Loading -> {
                }
            }
        }
    }
    // -------------------------------------------------------


}