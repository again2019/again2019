package com.example.presentation.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.domain.model.SavedTimeAboutRankModel
import com.example.domain.model.WhatToDoMonthModel
import com.example.domain.model.WhatToDoYearModel
import com.example.domain.util.UiState
import com.example.domain.util.currentday
import com.example.domain.util.toast
import com.example.presentation.PrefUtil
import com.example.presentation.R
import com.example.presentation.databinding.BottomSheetInputBinding
import com.example.presentation.ui.input.InputViewModel
import com.example.presentation.ui.tutorial.TutorialActivity

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class InputBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding : BottomSheetInputBinding
    private val viewModel : InputViewModel by activityViewModels()

    private var InitWhatToDoMonthList = ArrayList<WhatToDoMonthModel>()
    private var InitWhatToDoYearList = ArrayList<WhatToDoYearModel>()
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
                    whatToDo.text = state.data.whatToDoList.toString().removeSurrounding("[","]")
                    var count = 0
                    for (whattodo in state.data.whatToDoList) {
                        whattodoList.add(whattodo)

                        // chart 표시를 위해 초기화 하기 위해 arrayList에 넣는 코드
                        val whatToDoMonthModel = WhatToDoMonthModel(
                            count = 0,
                            month = currentday("MM").toInt(),
                            whatToDo = whattodo
                        )
                        InitWhatToDoMonthList.add(whatToDoMonthModel)
                        val whatToDoYearModel = WhatToDoYearModel(
                            count = 0,
                            year = currentday("yyyy").toInt(),
                            whatToDo = whattodo
                        )
                        InitWhatToDoYearList.add(whatToDoYearModel)
                        count += 1
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

            val rankMonthDTO =  SavedTimeAboutRankModel(
                uid = PrefUtil.getCurrentUid(requireContext())!!,
                count = 0,
                nickname = nickName.text.toString()
            )
            viewModel.addInitRankMonthTime(rankMonthDTO)
            val rankYearDTO =  SavedTimeAboutRankModel(
                uid = PrefUtil.getCurrentUid(requireContext())!!,
                count = 0,
                nickname = nickName.text.toString()
            )
            viewModel.addInitRankYearTime(rankYearDTO)

            moveTutorialPage()
        }
        xBtn.setOnClickListener {
            dialog!!.dismiss()
        }
        noButton.setOnClickListener {
            dialog!!.dismiss()
        }

    }

    private fun moveTutorialPage() {
        val intent = Intent(requireActivity(), TutorialActivity::class.java)
        startActivity(intent)
    }


}