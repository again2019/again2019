package com.goingbacking.goingbacking.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Tutorial.TutorialActivity
import com.goingbacking.goingbacking.UI.Input.InputViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetInputBinding
import com.goingbacking.goingbacking.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class InputBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetInputBinding
    private val viewModel : InputViewModel by activityViewModels()

    private var InitWhatToDoMonthList = ArrayList<WhatToDoMonthDTO>()
    private var InitWhatToDoYearList = ArrayList<WhatToDoYearDTO>()
    private var whattodoList = mutableSetOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

                    for (whattodo in state.data.whatToDoList) {
                          whattodoList.add(whattodo)

                          // chart 표시를 위해 초기화 하기 위해 arrayList에 넣는 코드
                          val whatToDoMonthDTO = WhatToDoMonthDTO(
                              count = 0,
                              month = currentday("MM").toInt(),
                              whatToDo = whattodo
                          )
                          InitWhatToDoMonthList.add(whatToDoMonthDTO)

                          val whatToDoYearDTO = WhatToDoYearDTO(
                              count = 0,
                              year = currentday("yyyy").toInt(),
                              whatToDo = whattodo
                          )

                          InitWhatToDoYearList.add(whatToDoYearDTO)
                          // ----------------------------------------------

                          // chip을 추가하는 코드
                          chipGroup.addView(Chip(requireActivity()).apply {
                                text = whattodo
                                isCheckable = false
                                isChecked = false
                          })
                        }

                }
                is UiState.Failure -> {
                    progressCircular.hide()
                    toast(requireContext(), getString(R.string.check_input_fail))
                }
                is UiState.Loading -> {
                    progressCircular.show()
                }
            }

        }
    }


    private fun onClick() = with(binding) {
        okayButton.setOnClickListener {
            for (initWhatToDoMonth in InitWhatToDoMonthList) {
                viewModel.addInitWhatToDoMonthTime(initWhatToDoMonth)
            }

            for (initWhatToDoYear in InitWhatToDoYearList) {
                viewModel.addInitWhatToDoYearTime(initWhatToDoYear)
            }

            PrefUtil.setHistoryWhatToDo(whattodoList, requireContext())

            PrefUtil.setCurrentUid(PrefUtil.firebaseUid(), requireContext())
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