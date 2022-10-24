package com.goingbacking.goingbacking.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Tutorial.TutorialActivity
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetInputBinding
import com.goingbacking.goingbacking.util.UiState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InputBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetInputBinding
    private val viewModel :InputViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetInputBinding.inflate(inflater, container, false)

        observer()
        onClick()
        return binding.root
    }


    private fun observer() = with(binding) {
        viewModel.checkInput()
        viewModel.checkUserInfo.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    progressCircular.hide()
                    nickName.text = state.data.userNickName
                    type.text = state.data.userType

                    if (state.data.whatToDo.equals("")) {

                    } else {
                        for (whattodo in state.data.whatToDo!!.split(',')) {
                            chipGroup.addView(Chip(requireActivity()).apply {
                                text = whattodo
                                isCheckable = false
                                isChecked = false
                            })
                        }
                    }
                }
                is UiState.Failure -> {
                    progressCircular.hide()
                    Toast.makeText(requireContext(), R.string.check_input_fail, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> {
                    progressCircular.show()
                }
            }

        }
    }


    private fun onClick() = with(binding) {
        okayButton.setOnClickListener {
            moveTutorialPage()
            
        }
        noButton.setOnClickListener {
            dismiss()
        }

    }

    private fun moveTutorialPage() {
        val intent = Intent(requireActivity(), TutorialActivity::class.java)
        startActivity(intent)
    }


}