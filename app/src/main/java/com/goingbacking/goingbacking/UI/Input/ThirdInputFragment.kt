package com.goingbacking.goingbacking.UI.Input

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
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

        binding.progressBar.setMinAndMaxProgress(0.355f, 0.63f)
        binding.progressBar.playAnimation()

        val menuHost: MenuHost = requireActivity()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    android.R.id.home -> {
                        findNavController().navigate(R.id.action_thirdInputFragment_to_secondInputFragment)
                        return true
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)



        onClick()
    }

    private fun onClick() = with(binding) {


        // 다음으로 가는 버튼
        thirdInputButton.setOnClickListener {

            val selected = mutableListOf<String>()
            chipGroup.checkedChipIds.forEach {
                val chip = root.findViewById<Chip>(it).text.toString()
                selected.add(chip)
            }

            Log.d("experiment", selected.toString())

            if (selected.equals("[]"))  {
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