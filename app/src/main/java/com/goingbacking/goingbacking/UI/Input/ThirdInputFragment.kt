package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.bottomsheet.InputBottomSheet
import com.goingbacking.goingbacking.databinding.FragmentThirdInputBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdInputFragment : BaseFragment<FragmentThirdInputBinding>() {
    val viewModel: InputViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentThirdInputBinding {
        return FragmentThirdInputBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()
    }

    private fun onClick() = with(binding) {
        // chip 추가하는 버튼
        chipAddButton.setOnClickListener {
            inputChipGroup.addView(Chip(requireContext()).apply {
                text = chipInputEditText.text.toString()
                isCloseIconVisible = true
                isCheckable = true
                isChecked = true
                setOnClickListener { isChecked = true }
                setOnCloseIconClickListener { inputChipGroup.removeView(this) }
                chipInputEditText.setText("")
            })
        }

        // 이전으로 가는 버튼
        ThirdInputButton1.setOnClickListener {
            findNavController().navigate(R.id.action_thirdInputFragment_to_secondInputFragment)
        }
        // 다음으로 가는 버튼
        ThirdInputButton2.setOnClickListener {

            val selected1 = chipGroup.children.toList()
                .filter{ (it as Chip).isChecked}.joinToString(",")
                {(it as Chip).text}
            val selected2 = inputChipGroup.children.toList()
                .filter{ (it as Chip).isChecked}.joinToString(",")
                {(it as Chip).text}

            var selected = ""
            if (selected1.equals(""))  {
                selected = selected2
            } else if (selected2.equals("")) {
                selected = selected1
            } else {
                selected = selected1 + ',' + selected2
            }

            // 데이터 베이스에 입력하는 코드
            viewModel.updateThirdInput(selected)
            // 입력이 성공적인지 확인하는 코드
            ThirdInputObserver()

            // bottom sheet으로 이동
            val bottom  = InputBottomSheet()
            bottom.show(childFragmentManager, bottom.tag)

        }
    }

    // 데이터 베이스에 입력하는 것이 잘되었는지 확인하는 함수
    private fun ThirdInputObserver() {
        viewModel.updateThirdInput.observe(viewLifecycleOwner) {
                state ->
            when(state) {
                is UiState.Failure -> {
                    binding.progressCircular.hide()
                }
                is UiState.Loading -> {
                    binding.progressCircular.show()
                }
                is UiState.Success -> {
                    binding.progressCircular.hide()
                }
            }
        }
    }
    // -------------------------------------------------------

}