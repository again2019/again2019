package com.goingbacking.goingbacking.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.goingbacking.goingbacking.Model.WhatToDoMonthDTO
import com.goingbacking.goingbacking.Model.WhatToDoYearDTO
import com.goingbacking.goingbacking.R
import com.goingbacking.goingbacking.UI.Tutorial.TutorialActivity
import com.goingbacking.goingbacking.ViewModel.InputViewModel
import com.goingbacking.goingbacking.databinding.BottomSheetInputBinding
import com.goingbacking.goingbacking.util.PrefUtil
import com.goingbacking.goingbacking.util.UiState
import com.goingbacking.goingbacking.util.toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class InputBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetInputBinding
    private val viewModel :InputViewModel by activityViewModels()


    private var now = LocalDate.now()
    private var Strnow1 = now.format(DateTimeFormatter.ofPattern("MM")).toInt()
    private var Strnow2 = now.format(DateTimeFormatter.ofPattern("yyyy")).toInt()
    private var InitWhatToDoMonthList = ArrayList<WhatToDoMonthDTO>()
    private var InitWhatToDoYearList = ArrayList<WhatToDoYearDTO>()
    private var whattodoList = mutableSetOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetInputBinding.inflate(inflater, container, false)

        Observer()
        onClick()
        return binding.root
    }


    private fun Observer() = with(binding) {
        viewModel.checkInput()
        viewModel.checkUserInfo.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UiState.Success -> {
                    progressCircular.hide()
                    nickName.text = state.data.userNickName
                    type.text = state.data.userType

                  if (!state.data.whatToDo.equals("")) {
                      for (whattodo in state.data.whatToDo!!.split(',')) {
                          whattodoList.add(whattodo)

                          // chart 표시를 위해 초기화 하기 위해 arrayList에 넣는 코드
                          val whatToDoMonthDTO = WhatToDoMonthDTO()
                          whatToDoMonthDTO.count = 0
                          whatToDoMonthDTO.month = Strnow1
                          whatToDoMonthDTO.whatToDo = whattodo

                          InitWhatToDoMonthList.add(whatToDoMonthDTO)

                          val whatToDoYearDTO = WhatToDoYearDTO()
                          whatToDoYearDTO.count = 0
                          whatToDoYearDTO.year = Strnow2
                          whatToDoYearDTO.whatToDo = whattodo

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
            val uidSet = PrefUtil.getHistoryUid(requireContext())
            if (uidSet == null || uidSet.size == 0) {
                val newSet = mutableSetOf<String>()
                newSet.add(PrefUtil.firebaseUid())
                PrefUtil.setHistoryUid(newSet, requireContext())
            } else {
                uidSet.add(PrefUtil.firebaseUid())
                PrefUtil.setHistoryUid(uidSet, requireContext())
            }
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