package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Base.BaseFragment
import com.goingbacking.goingbacking.bottomsheet.InputBottomSheet
import com.goingbacking.goingbacking.databinding.FragmentThirdInputBinding
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
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


        // 이전으로 가는 버튼
        ThirdInputButton1.setOnClickListener {
            findNavController().navigate(R.id.action_thirdInputFragment_to_secondInputFragment)
        }
        // 다음으로 가는 버튼
        ThirdInputButton2.setOnClickListener {

            val selected = mutableListOf<String>()
            chipGroup.checkedChipIds.forEach {
                val chip = root.findViewById<Chip>(it).text.toString()
                selected.add(chip)
            }

            if (selected.equals(""))  {
                toast(requireContext(), getString(R.string.chip_no_selected))
            } else {
                // 데이터 베이스에 입력하는 코드
                // 입력이 성공적인지 확인하는 코드
                observer(selected.toList())

                // bottom sheet으로 이동
                val bottom  = InputBottomSheet()
                bottom.show(childFragmentManager, bottom.tag)
            }
        }
    }




    // 데이터 베이스에 입력하는 것이 잘되었는지 확인하는 함수
    private fun observer(selected : List<String>) {
        viewModel.updateThirdInput(selected)
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