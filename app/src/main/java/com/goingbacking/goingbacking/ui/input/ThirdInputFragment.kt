package com.goingbacking.goingbacking.ui.input

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.util.UiState
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.ui.base.BaseFragment
import com.goingbacking.goingbacking.bottomsheet.InputBottomSheet
import com.goingbacking.goingbacking.databinding.FragmentThirdInputBinding
import com.goingbacking.goingbacking.util.NetworkManager
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

        val args : ThirdInputFragmentArgs by navArgs()
        binding.progressBar.setMinAndMaxProgress(0.355f, 0.63f)
        binding.progressBar.playAnimation()


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        toast(requireContext(), "자기계발 최대 3개 선택해주세요.")
        binding.backbtn.setOnClickListener {
            val action = ThirdInputFragmentDirections.actionThirdInputFragmentToSecondInputFragment(args.nickname)
            findNavController().navigate(action)
        }

        onClick()
    }

    private fun onClick() = with(binding) {
        // 다음으로 가는 버튼
        thirdInputButton.setOnClickListener {
            if (!NetworkManager.checkNetworkState(requireContext())) {
                toast(requireContext(), getString(R.string.network_fail))
            } else {
                val selected = mutableListOf<String>()
                chipGroup.checkedChipIds.forEach {
                    val chip = root.findViewById<Chip>(it).text.toString()
                    selected.add(chip)
                }

                if (selected.size == 0)  {
                    toast(requireContext(), getString(R.string.chip_no_selected))
                } else if (selected.size > 3) {
                    toast(requireContext(), "3개 이하로 선택해주세요.")
                }
                else {
                    // 데이터 베이스에 입력하는 코드
                    // 입력이 성공적인지 확인하는 코드
                    observer(selected.toList())

                    // bottom sheet으로 이동
                    binding.progressCircular.hide()
                    val bottom  = InputBottomSheet()
                    bottom.show(childFragmentManager, bottom.tag)
                }
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